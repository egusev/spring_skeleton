Project skeleton based on Spring MVC and Spring Security.

It implements the follow features:
1. Authentication user by the username and organization instead of using only username for the standard way of spring authentication.
2. The custom authorisation service that is used to check permissions for a webpage. The role's permissions are defined in the database.
3. Audit. It stores any change of any entity (inherited from the BaseEntity) into the database.
4. It logs accesses of any webpage (that comes through Spring Security) including the login and logout calls.
5. Themyleaf 2.1 is used as views.
6. It uses WRO for getting JS and CSS dependencies.

General information

1. Annotations are used to configure the spring application. All java configuration files are stored in the ru.erfolk.config package.
   User related configurations (like database connection) are stored in the src/resources/application.properties file.
2. Package ru.erfolk.audit contains the audit and log related classes -- Log and Audit entities, repositories, services. 
   They are placed separated of the business objects because they aren't a business objects. 
3. The package ru.erfolk.security contains classes which implement the custom authentication and authorisation features.
4. The package ru.erfolk.actors contains spring security entities (Actor, Role, UserAuthority).
5. The packages entities, repositories and services are used to store business objects.
6. The package web is used to store objects, related to the web application.
   It stores only controller now but others packages (dto, validators, converters etc) could be created further.
7. The views are stored in the src/main/webapp/WEB-INF/templates directory.
   The templates are based on the layout feature (http://www.thymeleaf.org/doc/articles/layouts.html)
   Each template contains only the main area while the markup and common items (header, menu, footer etc) are defined in the base layout (layout.html, menu.html) 