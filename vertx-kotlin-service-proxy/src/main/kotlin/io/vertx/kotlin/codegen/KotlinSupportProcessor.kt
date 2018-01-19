package io.vertx.kotlin.codegen

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import io.vertx.codegen.*
import io.vertx.codegen.annotations.DataObject
import io.vertx.codegen.annotations.ModuleGen
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.codegen.type.ClassKind
import io.vertx.codegen.type.TypeNameTranslator
import org.jetbrains.annotations.NotNull
import org.mvel2.MVEL
import io.vertx.kotlin.codegen.model.KotlinMethodInfo
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.regex.Pattern
import javax.annotation.processing.*
import javax.lang.model.element.*
import javax.tools.Diagnostic
import javax.tools.StandardLocation
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Create by yan on 2017/12/25 11:10
 */
@SupportedOptions("codegen.output", "codegen.generators")
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
class KotlinSupportProcessor : AbstractProcessor() {
    private val f = FileWriter("/tmp/test.log")
    private val java = 0
    private val kotlin = 3
    private val resources = 1
    private val other = 2
    private val mapper = ObjectMapper()
    private var outputDirectory: File? = null
    private var codeGenerators: List<CodeGenerator>? = null
    private val generatedFiles = HashMap<String, GeneratedFile>()
    private val generatedResources = HashMap<String, GeneratedFile>()
    private var relocations: Map<String, String> = HashMap()

    private fun println(msg : String?){
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, msg)
        f.write(msg + "\r\n")
        f.flush()
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
//        println("============getSupportedAnnotationTypes")
        return Arrays.asList(
                VertxGen::class.java,
                ProxyGen::class.java,
                DataObject::class.java,
                DataObject::class.java,
                ModuleGen::class.java,
                NotNull::class.java
        ).asSequence().map{ it.name }.toSet()
    }

    @Synchronized override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        println("${System.getProperty("codegen.output")}")
//        processingEnv.options["codegen.output"]
//        println("============init:" + processingEnv.elementUtils.javaClass)
        generatedFiles.clear()
        generatedResources.clear()
    }

    private fun filterGenerators(): ((CodeGenerator) -> Boolean)? {
//        println("============filterGenerators")
        var generatorsOption: String? = processingEnv.options["codegen.generators"]
        if (generatorsOption == null) {
            generatorsOption = processingEnv.options["codeGenerators"]
            if (generatorsOption != null) {
//                log.warning("Please use 'codegen.generators' option instead of 'codeGenerators' option")
                println("Please use 'codegen.generators' option instead of 'codeGenerators' option")
            }
        }
        if (generatorsOption != null) {
            val wanted = generatorsOption.split(",").asSequence().map(String::trim).map(Pattern::compile).toList()
            return { cg ->
                wanted.asSequence().filter { it.matcher(cg.name).matches() }.firstOrNull() != null
            }
        } else {
            return null
        }
    }

    private  fun loadGenerators(): List<CodeGenerator> {
        val generators = ArrayList<CodeGenerator>()
        var descriptors = Collections.emptyEnumeration<URL>()
        try {
            descriptors = KotlinSupportProcessor::class.java.classLoader.getResources("codegen.json")
        } catch (ignore: IOException) {
            processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "Could not load code generator descriptors")
        }

        val templates = HashSet<String>()
        while (descriptors.hasMoreElements()) {
            val descriptor = descriptors.nextElement()
            try {
                Scanner(descriptor.openStream(), "UTF-8").useDelimiter("\\A").use { scanner ->
                    val s = scanner.next()
                    val obj = mapper.readTree(s) as ObjectNode
                    val name = obj.get("name").asText()
                    val generatorsCfg = obj.get("generators") as ArrayNode
                    for (generator in generatorsCfg) {
                        val kind = generator.get("kind").asText()
                        var templateFilenameNode: JsonNode? = generator.get("templateFilename")
                        if (templateFilenameNode == null) {
                            templateFilenameNode = generator.get("templateFileName")
                        }
                        val templateFilename = templateFilenameNode!!.asText()
                        var filenameNode: JsonNode? = generator.get("filename")
                        if (filenameNode == null) {
                            filenameNode = generator.get("fileName")
                        }
                        val filename = filenameNode!!.asText()
                        val incremental = generator.has("incremental") && generator.get("incremental").asBoolean()
                        if (!templates.contains(templateFilename)) {
                            templates.add(templateFilename)
                            generators.add(CodeGenerator(name, kind, incremental, filename, templateFilename))
                        }
                    }
                }
            } catch (e: Exception) {
                val msg = "Could not load code generator " + descriptor
//                log.log(Level.SEVERE, msg, e)
                println(msg + e.message)
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, msg)
            }

        }
        return generators
    }

    private fun getCodeGenerators(): Collection<CodeGenerator> {
        if (codeGenerators == null) {
            println("${processingEnv.options}")
            var outputDirectoryOption: String? = processingEnv.options["codegen.output"]
            if (outputDirectoryOption == null) {
                outputDirectoryOption = processingEnv.options["outputDirectory"]
                if (outputDirectoryOption != null) {
                    println("Please use 'codegen.output' option instead of 'outputDirectory' option")
                }
            }
            if (outputDirectoryOption != null) {
                outputDirectory = File(outputDirectoryOption)
                if (!outputDirectory!!.exists()) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Output directory $outputDirectoryOption does not exist")
                }
                if (!outputDirectory!!.isDirectory) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Output directory $outputDirectoryOption is not a directory")
                }
            } else {
                println("Please use 'codegen.output' option set outputDirectory")
            }
            var generators = loadGenerators()
            val filter = filterGenerators()
            if (filter != null) {
                generators = generators.asSequence().filter(filter).toList()
            }
            generators.forEach { gen ->
                val template = Template(gen.templateFilename)
                template.setOptions(processingEnv.options)
                gen.template = template
                gen.filenameExpr = MVEL.compileExpression(gen.filename)
                println("Loaded " + gen.name + " code generator")
            }
            relocations = processingEnv.options.asSequence().filter { it.key.startsWith("codegen.output.") }.associateBy({it.key.substring("codegen.output.".length)}, Map.Entry<String, String>::value)

            codeGenerators = generators
        }
        return codeGenerators!!
    }

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        println("====================")
        if (!roundEnv.processingOver()) {
            val codeGenerators = getCodeGenerators()

            if (!roundEnv.errorRaised()) {
                val codegen = CodeGen(processingEnv, roundEnv)
                val generatedClasses = HashMap<String, GeneratedFile>()

                // Generate source code
                codegen.models.forEach { entry ->
                    try {
                        val model = entry.value
                        val vars = HashMap<String, Any>()
                        vars["helper"] = Helper()
                        vars["options"] = processingEnv.options
                        vars["fileSeparator"] = File.separator
                        vars["fqn"] = model.fqn
                        vars["module"] = model.module
                        vars["model"] = model
                        vars.putAll(model.vars)
                        vars.putAll(ClassKind.vars())
                        vars.putAll(MethodKind.vars())
                        vars.putAll(Case.vars())
                        for (codeGenerator in codeGenerators) {
                            vars.putAll(TypeNameTranslator.vars(codeGenerator.name))
                            if (codeGenerator.kind == model.kind) {
                                var relativeName: String? = MVEL.executeExpression(codeGenerator.filenameExpr, vars) as String?
                                if (relativeName != null) {

                                    val kind: Int
                                    println("==========relativeName:$relativeName")
                                    println("==========relocation:${relocations[codeGenerator.name]}")
                                    if (relativeName.endsWith(".java") && !relativeName.contains("/")) {
                                        val relocation = relocations[codeGenerator.name]
                                        if (relocation != null) {
                                            kind = other
                                            relativeName = relocation + '/' +
                                                    relativeName.substring(0, relativeName.length - ".java".length).replace('.', '/') + ".java"
                                        } else {
                                            kind = java
                                        }
                                    } else if (relativeName.startsWith("resources/")) {
                                        kind = resources
                                    } else if (relativeName.endsWith(".kt")) {
                                        println("====kotlin")
                                        val relocation = relocations[codeGenerator.name]
                                        if (relocation != null) {
                                            kind = other
                                            relativeName = relocation + '/' +
                                                    relativeName.substring(0, relativeName.length - ".kt".length).replace('.', '/') + ".kt"
                                        } else {
                                            kind = kotlin
                                        }
                                    }else {
                                        kind = other
                                    }
                                    if (kind == java) {
                                        // Special handling for .java
                                        val fqn = relativeName.substring(0, relativeName.length - ".java".length)
                                        // Avoid to recreate the same file (this may happen as we unzip and recompile source trees)
                                        if (processingEnv.elementUtils.getTypeElement(fqn) != null) {
                                            continue
                                        }
                                        val processings = generatedClasses.computeIfAbsent(fqn, ::GeneratedFile)
                                        processings.add(ModelProcessing(model, codeGenerator))
                                    } else if (kind == kotlin) {
                                        // Special handling for .java
                                        val fqn = relativeName.substring(0, relativeName.length - ".kt".length)
                                        // Avoid to recreate the same file (this may happen as we unzip and recompile source trees)
                                        if (processingEnv.elementUtils.getTypeElement(fqn) != null) {
                                            continue
                                        }
                                        val processings = generatedFiles.computeIfAbsent(fqn.replace(".", "/") + ".kt", ::GeneratedFile)
                                        processings.add(ModelProcessing(model, codeGenerator))
                                    } else if (kind == resources) {
                                        relativeName = relativeName.substring("resources/".length)
                                        val processings = generatedResources.computeIfAbsent(relativeName, ::GeneratedFile)
                                        processings.add(ModelProcessing(model, codeGenerator))
                                    } else {
                                        val processings = generatedFiles.computeIfAbsent(relativeName, ::GeneratedFile)
                                        processings.add(ModelProcessing(model, codeGenerator))
                                    }
                                }
                            }
                        }
                    } catch (e: GenException) {
                        reportGenException(e)
                    } catch (e: Exception) {
                        reportException(e, entry.key)
                    }
                }
                // Generate classes
                generatedClasses.values.forEach { generated ->
                    try {
                        val content = generated.generate()
                        if (content.isNotEmpty()) {
                            val target = processingEnv.filer.createSourceFile(generated.uri)
                            target.openWriter().use { writer -> writer.write(content) }
//                            log.info("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                            println("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                        }
                    } catch (e: GenException) {
                        reportGenException(e)
                    } catch (e: Exception) {
                        reportException(e, generated[0].model.element)
                    }
                }
            }
        } else {
            // Generate resources
            for (generated in generatedResources.values) {
                try {
                    val content = generated.generate()
                    if (content.isNotEmpty()) {
                        processingEnv.filer.createResource(StandardLocation.CLASS_OUTPUT, "", generated.uri).openWriter().use { w -> w.write(content) }
                        val createSource: Boolean = try {
                            processingEnv.filer.getResource(StandardLocation.SOURCE_OUTPUT, "", generated.uri)
                            true
                        } catch (e: FilerException) {
                            // SOURCE_OUTPUT == CLASS_OUTPUT
                            false
                        }

                        if (createSource) {
                            processingEnv.filer.createResource(StandardLocation.SOURCE_OUTPUT, "", generated.uri).openWriter().use { w -> w.write(content) }
                        }
//                        log.info("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                        println("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                    }
                } catch (e: GenException) {
                    reportGenException(e)
                } catch (e: Exception) {
                    reportException(e, generated[0].model.element)
                }

            }
            println("============write")
            // Generate files
            if (outputDirectory != null) {
                println("============write222")
                generatedFiles.values.forEach { generated ->
                    val file = File(outputDirectory, generated.uri)
                    if (!file.parentFile.exists()) {
                        file.parentFile.mkdirs()
                    }
                    println("=========contextcontexteoncasdf")
                    val content = generated.generate()
                    if (content.isNotEmpty()) {
                        try {
                            FileWriter(file).use { fileWriter -> fileWriter.write(content) }
                        } catch (e: GenException) {
                            reportGenException(e)
                        } catch (e: Exception) {
                            reportException(e, generated[0].model.element)
                        }

//                        log.info("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                        println("Generated model " + generated[0].model.fqn + ": " + generated.uri)
                    }
                }
            }
        }
        return true
    }

    private fun reportGenException(e: GenException) {
        println("============reportGenException")
        val msg = "Could not generate model for " + e.element + ": " + e.msg
//        log.log(Level.SEVERE, msg, e)
        println(msg + e.message)
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, msg, e.element)
    }

    private fun reportException(e: Exception, elt: Element) {
        println("============reportException")
        val msg = "====Could not generate element for " + elt + ": " + e.message
        println(msg + e.javaClass)

        printlnException(e)

        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, msg, elt)
    }

    private fun printlnException(e : Throwable){
        val stacks = e.stackTrace
        for (s in stacks) {
            println("@ ${s.className}.${s.methodName}(${s.fileName}:${s.lineNumber})")
        }
        if (e.cause != null) {
            println("cause :")
            printlnException(e.cause!!)
        }
    }

    private class ModelProcessing(internal val model: Model, internal val generator: CodeGenerator)

    private inner class GeneratedFile(internal val uri: String) : ArrayList<ModelProcessing>() {
        internal val session: Map<String, Any> = HashMap()

        override fun add(element: ModelProcessing): Boolean {
            if (!element.generator.incremental) {
                clear()
            }
            return super.add(element)
        }

        internal fun generate(): String {
            Collections.sort(this) { o1, o2 ->
                o1.model.element.simpleName.toString().compareTo(
                        o2.model.element.simpleName.toString())
            }
            var index = 0
            val buffer = StringBuilder()
            for (i in 0 until size) {
                val processing = get(i)
                val vars = HashMap<String, Any>()
                vars.putAll(TypeNameTranslator.vars(processing.generator.name))
                if (processing.generator.incremental) {
                    vars["incrementalIndex"] = index++
                    vars["incrementalSize"] = size
                    vars["session"] = session
                }
                try {
                    if (uri.endsWith(".tk")) {
                        if (processing.model is ProxyModel) {
                            val kotlinMethods = ArrayList<KotlinMethodInfo>()
                            val field = ClassModel::class.java.getDeclaredField("methods")
                            if (field != null) {
                                field.isAccessible = true
                                @Suppress("UNCHECKED_CAST")
                                val methods = field.get(processing.model) as LinkedHashMap<ExecutableElement, MethodInfo>
                                methods.mapTo(kotlinMethods) { (element, info)  ->
                                    KotlinMethodInfo(info, element, processingEnv.messager)
                                }
//                                println("=====${field.name}===${map.size}")
//                                map.keys.forEach {
//                                    it.parameters.forEach { p->
//                                        println("=========p.annotations" + p.annotationMirrors.size)
//                                        p.annotationMirrors.forEach {
//                                            println("======p.a.elements${it.annotationType}")
//                                        }
//                                    }
//                                }
                            }
                            vars["kotlinMethods"] = kotlinMethods
                        }
                    }

                    val part = processing.generator.template!!.render(processing.model, vars)
                    if (part != null) {
                        buffer.append(part)
                    }
                } catch (e: GenException) {
                    throw e
                } catch (e: Exception) {
//                    throw GenException(processing.model.element, e.message)
                    throw e
                }

            }
            return buffer.toString()
        }
    }
}