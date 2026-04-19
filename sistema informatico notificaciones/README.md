# Sistema de Notificaciones Universitarias v2.0

Este proyecto es una solución integral diseñada para que una universidad gestione y registre el envío de notificaciones académicas y administrativas a través de múltiples canales (Email, SMS y Notificaciones Push).

## 🚀 Características

- **Interfaz Premium**: Dashboard moderno desarrollado en Java Swing con gráficos personalizados y flujo de trabajo optimizado.
- **Gestión de Situaciones**:
  - Publicación de calificaciones.
  - Recordatorio de pago de matrícula.
  - Aviso de cancelación de clase.
  - Confirmación de inscripción a eventos académicos.
- **Multicanal**: Soporte extensible para Correo Electrónico, Mensajería de Texto (SMS) y Aplicación Móvil (Push).
- **Registro de Auditoría**: Tabla visual que registra código, destinatario, fecha, mensaje y estado de cada notificación enviada.

## 🏗️ Arquitectura y Patrones de Diseño

El sistema ha sido diseñado bajo principios de programación orientada a objetos (POO) para ser altamente escalable:

1.  **Patrón Strategy**: Desacopla la lógica de envío de los modelos de notificación, permitiendo añadir nuevos medios (ej. WhatsApp) sin rediseñar la solución.
2.  **Patrón Factory**: Centraliza la creación de objetos de notificación, simplificando la gestión de datos específicos por medio.
3.  **Open/Closed Principle**: El sistema está preparado para crecer en medios y situaciones sin modificar el núcleo de la lógica.

## 📋 Instrucciones de Ejecución

Para ejecutar el sistema, asegúrate de tener instalado el JDK (Java Development Kit) y sigue estos pasos desde la terminal en la raíz del proyecto:

### 1. Compilación
Compila todas las clases dentro del paquete unificado:
```bash
javac -d . notificaciones/*.java
```

### 2. Ejecución
Inicia la aplicación con el siguiente comando:
```bash
java notificaciones.Main
```

## 📁 Estructura del Proyecto

- `notificaciones/`: Carpeta unificada que contiene todas las clases del sistema.
  - `Main.java`: Punto de entrada de la aplicación.
  - `AppWindow.java`: Motor de la interfaz gráfica.
  - `Notification.java`: Clase base abstracta.
  - `*Channel.java`: Implementaciones de estrategias de envío.
  - `NotificationFactory.java`: Fábrica de notificaciones.
  - `NotificationService.java`: Servicio de orquestación.

---
**Desarrollado para la Universidad - 2026**
