# company-ma-inner-api-socle
A template for backend microservices


A spring boot skeleton (template), for company
inner APIs.

## Requirements
> Please follow [Developer Workplace](#) wiki to get/install your devTools.
* [Open JDK-17+](https://github.company
* .com/company
* -ma/company
* -ma-documentation/wiki/Developer-Workplace#java)
* [Maven 3](https://github.company
* .com/company
* -ma/company
* -ma-documentation/wiki/Developer-Workplace#maven)
* [Intellij IDE](https://github.company
* .com/company
* -ma/company
* -ma-documentation/wiki/Developer-Workplace#spring-tool-suite)
    - Mandatory Plugins: Lombok
> Make sure you'r using OpenJDK-17, check your java version with : `java -version`.

## How create new project from this Template

Please follow this wiki [Creating a Repository from a Template](https://github.company
.com/company
-ma/company
-ma-documentation/wiki/Create-First-Java-Project)

## Project Structure:

### Main folders

The project follows a standard spring project structure,is decomposed into multiple folders:

- **api**: contains different server Rest Controller, defining the various endpoints.
- **config**: contains project configuration like: openApi, security and web config.
- **domain.data**: contains the app's business objects.
- **domain.dto**: contains objects representing requests and responses for various external systems (Client services, company
- Inner Services).
- **enums**: contains Enumerations.
- **exception**: contains objects used in exceptions management.
- **mapper**: contains the app's mapper classes.
- **repository**: contains the database communication layer.
- **service**: contains the service layer.
- **util**: contains utility classes.
- **util/constants**: contains constants classes.

### Test folders

All Test classes, must be under `/src/main/test`:

- **api**: contains unit testing for API layer.
- **repository**: contains Unit testing for repository layer.
- **service**: contains Unit testing for service layer.
- **util**: contains Test utility classes.

## Run & Build application

### Locally

1. To build the project locally, run the following command:

```
 mvn clean install
```

2. To run the application locally, run the following command:

```
 mvn spring-boot:run
```

### With Jenkins
1. Update file `cicd/jenkins.properties`: replace apps parameters with your application name, ...
2. Create your project on jenkins, follow the instructions [WIKI](https://confluence.company
3. .com/confluence/pages/viewpage.action?pageId=290631816)
3. To expose this API, You SHOULD add Nginx config on one of the following space:
    - [Inner Gateway](https://github.company
    - .com/company
    - -ma/company
    - -ma-gateway/tree/master/inner).


## Test your Rest APIs with Postman

1. Open your Postman. (If not installed yet, follow [Install Postman](https://github.company
2. .com/company
3. -ma/company
4. -ma-documentation/wiki/Developer-Workplace#postman))
2. Read the wiki [How to test your APIs](https://github.company
3. .com/company
4. -ma/company
5. -ma-documentation/wiki/Postman#how-to-test-your-apis-with-postman).

## Security Management

1. To enable/disable spring security, You must update the property `company
2. .security.enabled` (by default is `true`) in the application.yml file :

     ```
      company
   :
        security:
          enabled: false
     ```
2. In case the spring security enabled, You should generate a Token and add it to your `http Authorization header`, Follow the wiki [How to Generate & add token with Postman](https://github.company
3. .com/company
4. -ma/company
5. -ma-documentation/wiki/Postman#how-to-generate--add-token-with-postman).

## links

| Tools                                                                                                                | Discription                             |
|----------------------------------------------------------------------------------------------------------------------| --------------------------------------- |
| [JIRA](https://jira.company<br/>.com/)                                                                                    | A proprietary issue tracking product.   |
| [Jenkins](https://jenkins-cicd-dev-company<br/>-ma.company<br/>-ma-dev-int.merlot.eu-central-1.aws.openpaas.company<br/>-cloud.com) | An open source automation server.       |
| [company<br/> MA Confluence](https://confluence.company<br/>.com/confluence/display/company<br/>MA/company<br/>+Morocco+Home)            | A web-based corporate wiki.                   |