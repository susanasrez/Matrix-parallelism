package org.ulpgc.matrixmultiplication.view;

import com.google.gson.Gson;
import org.ulpgc.matrixmultiplication.MatrixConsoleUtil;
import org.ulpgc.matrixmultiplication.consoleoutput.MatrixMultiplicationResultPresenter;
import org.ulpgc.matrixmultiplication.consoleoutput.TimeResults;

import static spark.Spark.get;
import static spark.Spark.port;

public class API {

    static MatrixConsoleUtil showResults = new MatrixMultiplicationResultPresenter();

    public static void runAPI(int port){
        port(port);
        getMultiplicationTiles();
    }

    public static void getMultiplicationTiles(){
        get("multiply/:number", (req, res) -> {
            int number = Integer.parseInt(req.params("number"));
            TimeResults timeResults = showResults.setUp(number);
            return (new Gson()).toJson(timeResults);
        });
    }
}
