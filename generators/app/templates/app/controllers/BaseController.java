package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

/**
 * Abstract Class representing an Base Controller.
 */
public abstract class BaseController extends Controller {

    private Result jsonResponse(Result result) {
        return result.as("application/json; charset=utf-8");
    }
}
