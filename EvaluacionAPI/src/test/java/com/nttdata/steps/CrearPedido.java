package com.nttdata.steps;

import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class CrearPedido {

    private static String URL = "https://petstore.swagger.io/v2/store";
    Response response;

    public void crearPedidoMascota(int idMascota, int cantidad, String fecha) {
        //asegurando que al menos se compre una mascota, que la cantidad sea valida; es decir, mayor a 0
        Assert.assertTrue("La cantidad de mascota minimo debe ser 0",cantidad>0);

        SerenityRest.given()
                .contentType("application/json")
                .accept("application/json")
                .relaxedHTTPSValidation()
                //se le envia el body con los datos
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"petId\": "+idMascota+",\n" +
                        "  \"quantity\": "+cantidad+",\n" +
                        "  \"shipDate\": \""+fecha+"\",\n" +
                        "  \"status\": \"placed\",\n" +
                        "  \"complete\": true\n" +
                        "}")
                .log().all()
                .post(URL+"/order")
                .then()
                .log().all()
        ;
    }


    public void validarCodigoRespuesta(int statusCode) {
        //vaida la respuesta que tiene en memoria con 200
        restAssuredThat(response -> response.statusCode(statusCode));
    }


    public void validoStatusyCondicionPedido(String status) {
        //busca el campo "status" y "complete" dentro del body de la respuesta y
        // se asegura que sea igual a "placed" y "true" respectivamente
        response= SerenityRest.lastResponse();
        restAssuredThat(response -> response.body("status", equalTo(status)));
        restAssuredThat(response -> response.body("complete", equalTo(true)));

    }

    public boolean consultarExistenciaPedido(int idPedido) {
        boolean existePedido= false; //por defecto asume que no existe
        //consultar si este idPedido a consultar existe
        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .log().all()
                .get(URL+ "/order/"+idPedido)
                .then()
                .log().all()
        ;

        if(lastResponse().statusCode()==200){
            existePedido= true;
        }
        return existePedido;
    }

    public void consultoInformacionPedido(int idPedido) {
        //guuardamos la respsuesta del request en el response para despues imprimir
        response= given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .log()
                .all()
                .get(URL+"/order/"+idPedido);

    }

    public void imprimirDatosPedido() {
        //immprimiento los datos de un pedido dado con todos sus campos
        System.out.println("Datos del Pedido:");
        //System.out.println(response.getBody().print());
        System.out.println("Pedido id: " + SerenityRest.lastResponse().body().path("id"));
        System.out.println("Pet id: " + SerenityRest.lastResponse().body().path("petId").toString());
        System.out.println("Cantidad: " + SerenityRest.lastResponse().body().path("quantity").toString());
        System.out.println("Fecha de envio: " + SerenityRest.lastResponse().body().path("shipDate").toString());
        System.out.println("Estado: " + SerenityRest.lastResponse().body().path("status").toString());
        System.out.println("Completo: " + SerenityRest.lastResponse().body().path("complete").toString());
    }
}
