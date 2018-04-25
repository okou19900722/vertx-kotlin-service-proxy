package model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class UserModel {
    public UserModel(){

    }
    public UserModel(JsonObject json){

    }
    private String name;

    public JsonObject toJson(){
        return JsonObject.mapFrom(this);
    }

}
