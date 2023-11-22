package org.ulpgc.matrixmultiplication.view;

import com.google.gson.Gson;
import org.ulpgc.matrixmultiplication.checker.ShowResults;
import org.ulpgc.matrixmultiplication.checker.TimeResults;

import static spark.Spark.get;
import static spark.Spark.port;

public class API {

    public static void runAPI(int port){
        port(port);
        getMultiplicationTiles();
    }

    public static void getMultiplicationTiles(){
        get("multiply/:number", (req, res) -> {
            int number = Integer.parseInt(req.params("number"));
            TimeResults timeResults = ShowResults.calculate(number);
            return (new Gson()).toJson(timeResults);
        });
    }
}
