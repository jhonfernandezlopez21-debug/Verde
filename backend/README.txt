README - Proyecto Gestión Paciente

Objetivo
-------
Este documento describe, con referencias exactas a los ficheros del proyecto, dónde y cómo se aplican los principios SOLID (incluyendo el Principio de Sustitución de Liskov - LSP), el Principio de Inversión de Dependencias (DIP), el Principio de Responsabilidad Única (SRP), y dónde están los tests unitarios. Además enumera al menos 8 elementos de Código Limpio presentes en el proyecto y explica por qué contribuyen a la legibilidad y mantenibilidad, especialmente en un diseño que respeta LSP.

Resumen rápido (qué encontrarás aquí)
------------------------------------
- Localización de componentes clave (controladores, servicios, repositorios, modelos).
- Mapas de los principios SOLID aplicados con ejemplos (archivo y método exacto).
- Explicación de LSP y cómo se cumple en el diseño actual.
- Veredicto sobre DIP y SRP con evidencia.
- Lista de tests unitarios existentes con qué cubren.
- 8 (o más) elementos de Código Limpio detectados y su justificación.
- Sugerencias pequeñas y de bajo riesgo para mejorar la claridad y el cumplimiento de principios.

Estructura del código: carpetas y archivos relevantes
----------------------------------------------------
- Servicios (lógica de negocio):
  - `src/main/java/com/Paciente/Gestion_Paciente/Seguro/SeguroService.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Paciente/PacienteServirces.java` (nota: nombre con typo)
  - `src/main/java/com/Paciente/Gestion_Paciente/Factura/FacturaService.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/HistorialMedico/HistorialService.java`

- Interfaces de servicio:
  - `src/main/java/com/Paciente/Gestion_Paciente/Seguro/ISeguroService.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Paciente/IPacienteService.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Factura/IFacturaService.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/HistorialMedico/IHistorialService.java`

- Repositorios (persistencia):
  - `src/main/java/com/Paciente/Gestion_Paciente/Seguro/ISeguroRepository.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Paciente/IPacienteRepositories.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Factura/IFacturaRepository.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/HistorialMedico/IHistorialRepository.java`

- Controladores (API REST):
  - `src/main/java/com/Paciente/Gestion_Paciente/Seguro/SeguroController.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Paciente/Pacientecontrollers.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Factura/FacturaController.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/HistorialMedico/HistorialController.java`

- Modelos (entidades JPA):
  - `src/main/java/com/Paciente/Gestion_Paciente/Seguro/SeguroModels.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Paciente/PacienteModels.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/Factura/FacturaModels.java`
  - `src/main/java/com/Paciente/Gestion_Paciente/HistorialMedico/HistorialMedicoModels.java`

- Tests unitarios (Junit + SpringBootTest):
  - `src/test/java/com/Paciente/Gestion_Paciente/Seguro/SeguroServiceTests.java`
  - `src/test/java/com/Paciente/Gestion_Paciente/Paciente/PacienteServiceTests.java`
  - `src/test/java/com/Paciente/Gestion_Paciente/Factura/FacturaServiceTests.java`
  - `src/test/java/com/Paciente/Gestion_Paciente/HistorialMedico/HistorialServiceTests.java`


Principios SOLID con ejemplos concretos
--------------------------------------
1) Principio de Responsabilidad Única (SRP)
   - Qué exige: Una clase debe tener una única responsabilidad / motivo de cambio.
   - Ejemplos en el proyecto:
     - `SeguroService` (`src/.../Seguro/SeguroService.java`) — responsabilidad única: CRUD de seguros (getAll, getById, save, delete).
     - `FacturaService` (`src/.../Factura/FacturaService.java`) — responsabilidad única: lógica de facturación y cálculo del total al guardar.
     - `HistorialService` (`src/.../HistorialMedico/HistorialService.java`) — responsabilidad única: CRUD y actualización de historiales.
   - Observación: `PacienteServirces` concentra la gestión del paciente, pero también coordina la creación/asignación de seguro y manejo de historial; sigue siendo una responsabilidad coherente (gestión de entidad Paciente) aunque realiza varias operaciones relacionadas — se considera aceptable, pero podría subdividirse si crece más (por ejemplo: un servicio dedicado a sincronización de seguro).

2) Principio de Abierto/Cerrado (Open/Closed)
   - Qué exige: Las entidades deben estar abiertas a la extensión, cerradas a la modificación.
   - Ejemplos:
     - Uso de interfaces como `IFacturaService`, `ISeguroService`, `IPacienteService` permite añadir nuevas implementaciones sin cambiar consumidores (controladores y tests que usan estas abstracciones).
     - Repositorios extienden `JpaRepository` y se pueden extender con métodos personalizados sin modificar llamadas existentes.

3) Principio de Sustitución de Liskov (LSP)
   - Qué exige: Los objetos de una subclase deben poder sustituir a los de la superclase sin alterar las propiedades deseables del programa.
   - Cómo se demuestra aquí:
     - Controladores dependen de interfaces (por ejemplo `ISeguroService` en `SeguroController.java`). Cualquier implementación que respete la firma de `ISeguroService` (por ejemplo `SeguroService`) puede sustituirse sin romper el controlador.
     - Los repositorios usan `JpaRepository` (por ejemplo `ISeguroRepository`) y Spring Data proporciona la implementación concreta; el servicio trabaja con la interfaz, por lo tanto LSP se respeta en la inyección de dependencias.
   - Nota: No hay jerarquías complejas de subclases en las entidades del proyecto (pocas o ninguna subclase de modelos), por lo que LSP se cumple principalmente mediante el correcto uso de interfaces y contratos.

4) Principio de Segregación de Interfaces (ISP)
   - Qué exige: Muchas interfaces específicas son mejores que una interfaz general y masiva.
   - Ejemplos:
     - `ISeguroService`, `IFacturaService`, `IPacienteService`, `IHistorialService` son interfaces pequeñas y enfocadas (CRUD básicos), evitando consumidores innecesarios de métodos que no usan.

5) Principio de Inversión de Dependencias (DIP)
   - Qué exige: Módulos de alto nivel no deben depender de módulos de bajo nivel; ambos deben depender de abstracciones.
   - Evaluación en el proyecto:
     - Controladores dependen de interfaces (abstracciones): `SeguroController` usa `ISeguroService` (interfaz), `Pacientecontrollers` usa `IPacienteService` — esto cumple DIP en la capa de presentación.
     - Servicios dependen de repositorios, pero lo hacen a través de interfaces que extienden `JpaRepository` (`ISeguroRepository`, `IFacturaRepository`). En ese sentido, servicios dependen de abstracciones de persistencia y no de implementaciones concretas — también cumple DIP.
     - Conclusión: En las capas principales (controlador -> servicio -> repositorio) las dependencias se inyectan a través de interfaces/abstracciones; por tanto el proyecto respeta DIP razonablemente bien.


Tests unitarios y su cobertura (qué y dónde)
--------------------------------------------
- Ubicación: `src/test/java/com/Paciente/Gestion_Paciente/...`
- Tipos: tests con `@SpringBootTest` y `@Transactional` (tests de integración ligera con contexto Spring y rollback automático).
- Tests existentes y qué validan:
  - `SeguroServiceTests.java`
    - `testSaveAndGetById`: guarda un seguro y comprueba que puede recuperarse por id (cubre `SeguroService.save` + `getById`).
    - `testDeleteInsuranceRemovesIt`: guarda un seguro y luego comprueba que `SeguroService.delete` lo elimina.
  - `PacienteServiceTests.java`
    - `testSavePacienteCreatesNewInsuranceWhenNoId`: guarda un paciente que contiene un seguro sin id y comprueba que se crea el seguro asociado.
    - `testGetPacienteReturnsList`: inserta un paciente y valida que `getPaciente()` devuelve una lista no vacía.
    - `testDeletePacienteReturnsTrueWhenExists`: guarda y borra un paciente, validando `deletePaciente` y la ausencia posterior.
  - `FacturaServiceTests.java`
    - `testSaveConCoberturaPorcentaje`: valida el cálculo de descuento del 50% (cubre la lógica de parsing de cobertura y cálculo del total en `FacturaService.save`).
    - `testSaveConLimiteNumerico`: valida la interpretación de `limiteCobertura` como valor numérico.
    - `testSaveUsesExistingPacienteIfIdProvided`: valida que si se pasa `Paciente` con id, se reutiliza la entidad existente.
  - `HistorialServiceTests.java`
    - `testSaveAndGetById`: guarda un historial y lo recupera.
    - `testUpdateExisting`: comprueba que `update` modifica campos del historial existente.

- Observaciones sobre pruebas:
  - La estrategia actual usa contexto Spring real (no mocks), lo que brinda buena cobertura de integración entre capas.
  - Tests usan `@Transactional` para aislamiento; es una práctica limpia para tests que tocan BD en memoria o la base configurada para pruebas.


Ocho (8) elementos de Código Limpio detectados y su justificación
----------------------------------------------------------------
Para cada elemento indico ejemplos en archivos concretos y por qué ayuda a legibilidad/mantenibilidad.

1) Nombres descriptivos y coherentes
   - Ejemplos: `SeguroService`, `FacturaService`, `getAll()`, `getById()`, `save()`, `delete()` en servicios; `SeguroModels`, `PacienteModels` para entidades.
   - Por qué ayuda: Los nombres explícitos reducen la carga cognitiva al leer el código — facilitan encontrar la responsabilidad de cada clase (mejor comprensión rápida y navegación).

2) Separación en capas (Controller -> Service -> Repository)
   - Ejemplos: `SeguroController` llama a `ISeguroService`; `SeguroService` usa `ISeguroRepository`.
   - Por qué ayuda: Hace explícitas las responsabilidades y facilita pruebas (mocks o sustituciones) y cambios en una capa sin afectar otras (dado que dependen de interfaces).

3) Uso de interfaces/abstracciones
   - Ejemplos: `ISeguroService`, `IFacturaService`, `IPacienteService`, repositorios `...Repository`.
   - Por qué ayuda: Permite sustituir implementaciones, facilitar testing y respetar LSP y DIP; reduce acoplamiento.

4) Métodos cortos y con propósito claro
   - Ejemplos: `HistorialService.update` realiza sólo la lógica de actualización de campos; `SeguroService` tiene métodos CRUD sencillos.
   - Por qué ayuda: Métodos pequeños son más fáciles de entender y probar; facilita detectar responsabilidades y errores.

5) Manejo explícito de Optional en retornos
   - Ejemplos: `getById` devuelve `Optional<...>` en varios servicios como `SeguroService.getById` y `FacturaService.getById`.
   - Por qué ayuda: Obliga a manejar el caso de ausencia, lo que reduce NPE y hace el flujo de control más evidente.

6) Tests automatizados y transaccionales
   - Ejemplos: `@SpringBootTest` + `@Transactional` en `*ServiceTests.java`.
   - Por qué ayuda: Tests que se ejecutan automáticamente y dejan la BD limpia ayudan a mantener regresiones a raya y aumentan confianza al refactorizar (importante para LSP al sustituir implementaciones).

7) Estructura de paquetes por dominio
   - Ejemplos: paquetes `Seguro`, `Paciente`, `Factura`, `HistorialMedico` bajo `com.Paciente.Gestion_Paciente`.
   - Por qué ayuda: Agrupa código relacionado, simplifica búsqueda y navegación, y facilita asignar permisos/ownership lógicos.

8) Validaciones y manejo razonable de errores en servicios
   - Ejemplos: `FacturaService.save` protege el cálculo con try/catch alrededor del parsing de cobertura; `PacienteServirces.deletePaciente` devuelve boolean y captura excepciones.
   - Por qué ayuda: Evita fallos no controlados, facilita exponer errores controlados a niveles superiores; mejora robustez.


Relación entre Código Limpio y LSP
---------------------------------
- Muchas de las prácticas anteriores (nombres claros, interfaces pequeñas, separación de capas, tests) hacen más sencillo cumplir LSP: cuando una clase o implementación se sustituye por otra (por ejemplo, una nueva versión de `ISeguroService`) es más probable que el nuevo objeto respete el contrato si los métodos son pequeños, bien nombrados y están cubiertos por tests.


Limitaciones, observaciones y pequeñas mejoras sugeridas (bajo riesgo)
---------------------------------------------------------------------
- Nombre de clase con typo: `PacienteServirces` debería renombrarse a `PacienteServices` o `PacienteService` para mantener consistencia. Esto es un cambio de API que requiere buscar y actualizar referencias (bajo riesgo si se hace cuidadosamente + pruebas).
- Añadir javadoc mínimo a interfaces públicas (servicios/repositorios) para clarificar contratos (precondiciones, postcondiciones), ayudará al cumplimiento de LSP y a futuros mantenimientos.
- Añadir tests mockeados (ej.: usar Mockito) para pruebas unitarias puras de servicios sin arrancar todo el contexto Spring, complementando los tests de integración.
- Extra: Considerar extraer la lógica de cálculo de descuento en `FacturaService.save` a una clase separada (ej.: `CoberturaCalculator`) para respetar aún más SRP y facilitar tests unitarios sobre parsing y reglas.


Mapping de requisitos del usuario -> Estado
-----------------------------------------
- Explicar SOLID: Done (con archivos y métodos de referencia).
- Explicar LSP: Done (con ejemplos de uso de interfaces y sustitución).
- Indicar Tests Unitarios: Done (listado de tests, qué cubren y dónde).
- Código Limpio: 8 elementos detectados y justificados: Done.
- DIP: Evaluación y veredicto: Done.
- SRP: Explicado con ejemplos: Done.


Cómo reproducir los tests localmente (rápido)
-------------------------------------------
Desde la raíz del proyecto (donde está `pom.xml`) ejecutar:

  mvn test

Los tests actuales usan `@SpringBootTest` y `@Transactional`, por lo que arrancarán un contexto Spring y harán rollback al terminar cada test.


Si quieres que haga cambios automáticos (p. ej. renombrar `PacienteServirces`, extraer calculadora de cobertura, o añadir tests mockeados), dímelo y lo implemento siguiendo la misma estrategia: editar código, ejecutar compilación/tests y dejar todo verde.


Fin del documento
-----------------

Archivo generado automáticamente: `README.txt` (en la raíz del proyecto).
Si deseas que lo convierta en `README.md` con formato Markdown o que añada secciones adicionales (diagramas, ejemplos de uso de la API, comandos curl), indícamelo y lo preparo.

