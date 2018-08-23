package tk.okou.vertx.kotlin.serviceproxy.annotation

annotation class Test(val name : String) {
}

fun main(args: Array<String>) {
    val c : Class<out Annotation> = Class.forName("kotlin.Metadata") as Class<out Annotation>
    val data = Test::class.java.getAnnotation(c)

}