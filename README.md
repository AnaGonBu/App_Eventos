# App_Eventos  
### EVENTOS CON SPRING BOOT MVS, JPA, SECURITY  
Una empresa se dedica a gestionar eventos.  
Cada evento es de un tipo distinto. Los tipos de evento son: concierto, despedida, cumpleaños, boda, comidas en restaurantes, y nos interesa un código que lo identifique y su descripción.  
A un evento se inscriben clientes, de los que debemos guardar los datos que figuran en la base de datos de usuario.  
Esta aplicación la van a manejar tres tipos de usuarios de momento:  
  ![image](https://github.com/user-attachments/assets/dc4d064e-99e5-4228-b8c7-96e89ac6db93)
- Los administradores de la aplicación(“ROLE_ADMON”)
  Tiene acceso a la gestión de todos los componentes de la aplicación: Usuarios,Perfiles, Tipos de Evento y Eventos.
  Como la gestión más importante es la de Eventos, la página principal es de eventos.
  Pero pulsando cualquiera de las otras opciones aparecerá una página semejante a esta donde poder administrar cada caso: usuarios, Perfiles, Tipos de evento
  ![image](https://github.com/user-attachments/assets/f7593baf-0bef-4a51-8350-60fcccaa7c42)

- Los clientes registrados(“ROLE_CLIENTE”)
  Un CLIENTE puede apuntarse a más de un evento, y de cada evento puede hacer como máximo reserva de hasta 10 personas, en la misma reserva(controlar).
  Puede gestionar sus reservas.
  ![image](https://github.com/user-attachments/assets/adeb91e8-0d35-4e49-a4e0-af63d218e102)

- Los invitados: usuarios sin registrar(“Anonymus”)
  Podrá entrar a la aplicación sin Autenticar, podrá ver los eventos, seleccionar los destacados, activos y cancelados.
  Podrá entrar en la pantalla de detalle para ver el detalle de los datos de un evento, pero NO podrá reservar el evento ni intentar entrar en mis reservas,
  en este caso le saldrá el formulario de Iniciar sesión, o registrarse en la aplicación.



