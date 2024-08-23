package com.nttdata.glue;

import com.nttdata.steps.CrearPedido;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class CrearPedidoStepsDefs {

    @Steps
    CrearPedido crearPedido;

    @When("creo el pedido de mascota con id {int}, cantidad {int} y fecha de envio el {string}")
    public void creoElPedidoDeMascotaConIdCantidadYFechaDeEnvioEl(int idMascota, int cantidad, String fecha) {
        crearPedido.crearPedidoMascota(idMascota,cantidad,fecha);
    }

    @Then("el código de respuesta es {int}")
    public void elCódigoDeRespuestaEs(int statusCode) {
        crearPedido.validarCodigoRespuesta(statusCode);
    }
    @And("valido que el status del pedido sea {string} y completo")
    public void validoQueElStatusDelPedidoSeaYCompleto(String status) {
        crearPedido.validoStatusyCondicionPedido(status);
    }

    @Given("ingreso un pedido con el id {int}")
    public void ingresoUnPedidoConElId(int idPedido) {
        //primero valido que el pedido ingresado exista con su idPedido
        Assert.assertTrue("No existe el pedido con el id ingresado",crearPedido.consultarExistenciaPedido(idPedido));
    }

    @When("consulto la informacion del pedido con el id {int}")
    public void consultoLaInformacionDelPedidoConElId(int idPedido) {
        crearPedido.consultoInformacionPedido(idPedido);
    }

    @Then("imprime datos del pedido")
    public void imprimeDatosDelPedido() {
        crearPedido.imprimirDatosPedido();
    }


}
