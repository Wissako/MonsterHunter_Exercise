🐉 Gremio Monster Hunter - API RESTful
📋 Descripción del Proyecto
Este proyecto es una aplicación Backend completa desarrollada con Spring Boot que simula la gestión de un Gremio de Cazadores del universo Monster Hunter.

El sistema permite gestionar un bestiario de monstruos, registrar nuevos cazadores, crear misiones y, lo más importante, gestionar los Registros de Caza (Hunting Logs) validando reglas de negocio complejas (como verificar que el rango del cazador sea suficiente para la dificultad de la misión).

🛠️ Tecnologías Utilizadas
Lenguaje: Java 21

Framework: Spring Boot 3.4.1 (Web, Data JPA, Validation)

Base de Datos: MySQL

Mapeo Objeto-Relacional (ORM): Hibernate & JPA

Librerías Adicionales:

Lombok: Para reducción de código repetitivo (Getters, Setters, Builders).

MapStruct: Para el mapeo eficiente entre Entidades y DTOs.

Validation API: Para validación de datos de entrada (@NotNull, @Positive, etc.).
Carga Inicial de Datos (DataLoader)
Al arrancar la aplicación, el sistema carga automáticamente datos iniciales desde archivos JSON (monsters.json y quests.json) para que la base de datos no esté vacía.

Incluye lógica para evitar duplicados al reiniciar el servidor.

🚀 Endpoints Principales (API)
🐾 Monstruos (/monsters)
GET /monsters: Listar todos los monstruos con sus hábitats y materiales.

GET /monsters/search?name=Rathalos: Buscar monstruos por nombre parcial.

GET /monsters/paged?page=0&size=5: (Nuevo) Listado paginado de monstruos.

GET /monsters/complete-search: Búsqueda compleja filtrando por nombre, debilidad y hábitat simultáneamente.

⚔️ Cazadores (/hunters)
GET /hunters: Listar todos los cazadores.

POST /hunters: Registrar un nuevo cazador.

Validación: No permite emails duplicados.

DELETE /hunters/{id}: Dar de baja a un cazador.

PUT /hunters/{id}: Actualizar datos del cazador.

📜 Misiones (/quests)
GET /quests: Listar misiones disponibles.

POST /quests: Crear una nueva misión (asignando un monstruo objetivo).

📖 Registros de Caza (/hunting-logs)
POST /hunting-logs: Registrar un intento de caza.

Regla de Negocio: El sistema verifica automáticamente si el Rango del Cazador >= Dificultad de la Misión. Si no cumple, lanza un error 400 y no guarda el registro.

GET /hunting-logs/hunter/{hunterId}: Ver el historial de caza de un cazador específico.

🏗️ Modelo de Datos (Entidades)
El proyecto cuenta con relaciones avanzadas entre entidades:

Hunter (1:N) HuntingLog: Un cazador tiene muchos registros.

Quest (1:N) HuntingLog: Una misión aparece en muchos registros.

Monster (N:M) Habitat: Un monstruo puede vivir en varios hábitats y un hábitat tener varios monstruos.

Monster (N:M) Material: Relación para gestionar los "drops" o materiales que suelta cada bestia.

🧪 Validaciones y Manejo de Errores
Se ha implementado un RestExceptionHandler global para manejar las excepciones de forma elegante y devolver JSONs limpios al cliente:

404 Not Found: Cuando no se encuentra un monstruo, cazador o misión.

400 Bad Request: Errores de validación (@Valid) o reglas de negocio (Email duplicado, Rango insuficiente).

👤 Autor
Luis Alfonso
