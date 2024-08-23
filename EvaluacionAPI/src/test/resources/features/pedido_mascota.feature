Feature: Gestion de pedido

  #escenario exitoso con un solo registro
  @crearPedido
  Scenario: Crear pedido exitoso
    When creo el pedido de mascota con id 1, cantidad 2 y fecha de envio el "2024-10-28"
    Then el código de respuesta es 200
    And valido que el status del pedido sea "placed" y completo

  #Ahora con escenario otuline con varios registros de pedido entre exitosos y no exitosos
  @crearPedidoMasivo
  Scenario Outline: Crear 2 pedidos exitosos y el ultimo no exitoso
    When creo el pedido de mascota con id <idMascota>, cantidad <cantidad> y fecha de envio el "<fecha>"
    Then el código de respuesta es 200
    And valido que el status del pedido sea "placed" y completo
    Examples:
      | idMascota | cantidad | fecha      | detalle sobre el pedido                                                |
      | 1         | 2        | 2024-08-28 | pedido exitoso                                                         |
      | 2         | 4        | 2024-09-14 | pedido exitoso                                                         |
      | 3         | 0        | 2024-15-11 | pedido no exitoso por tener la cantidad igual a 0, se espera que falle |

  #se consulta el pedido creado anteriormente
  @consultarPedido
  Scenario: Obtener los datos de un pedido existente
    Given ingreso un pedido con el id 1
    When consulto la informacion del pedido con el id 1
    Then imprime datos del pedido
    And el código de respuesta es 200


  #Ahora con escenario otuline con consulta de pedido entre exitosos y no exitosos
  @consultarPedidosMasivo
  Scenario Outline: Obtener los datos de varios pedidos validando primero su existencia
    Given ingreso un pedido con el id <idPedido>
    When consulto la informacion del pedido con el id <idPedido>
    Then imprime datos del pedido
    And el código de respuesta es 200
    Examples:
      | idPedido | Detalle del pedido                       |
      | 1        | Pedido existente                         |
      | 3        | Pedido existente                         |
      | 55       | Pedido no existente, se espera que falle |

