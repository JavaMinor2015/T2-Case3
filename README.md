<<<<<<< Updated upstream
# T2-Case3
Kantilever Project


## Setup

___Repo 1: T2C3-Spring___

* Spring Backend (/w Spring JPA/Hibernate)
* Spring REST (mogelijk met Spring HATEOAS)

___Repo 2: T2C3-Angular___

De Frontend voor de webwinkel.


___Repo 3: T2C3-Android___

* Android 5.0+
* Retrofit for Rest
* MVP pattern?
* Robolectric Test Platform


## Spring Structuur

* __Modules__ 
	* __Domain__ 
		_Entiteiten die in alle modules gebruikt worden_
		
	* __PlatformServices__ 
		_Processen/utilities die in alle modules gebruikt kunnen worden, bijv. abstracte repository_

	* __BS_Catalogus__
		_Catalogus implementatie, bijv. repository implementaties_
		
	* __BS_Voorraadbeheer__
		_Voorraadbeheer implementatie met integratieservice / servicebus_   
		
	* __BS_Klantbeheer__
		_Klantbeheer implementatie, bijv. repository implementaties_   
		
	* __BS_Bestellingbeheer__
		_Bestellingbeheer implementatie, bijv. repository implementaties_
		
	* __PCS_Winkelen__
		_Processlogica voor winkelen, alle servicemethoden die aangeroepen worden door de frontends_
		
	* __PCS_Bestellen__
		_Processlogica (REST) voor bestellen, alle servicemethoden die aangeroepen worden door de android frontend_
       
        
        
## Git

* master - alleen releases via een release branch
* dev - features/bugs etc. die afgerond zijn
* release/naam - een nieuwe release voorbereiden
* feature/naam - een nieuwe feature voorbereiden

__Voor merge naar dev:__

Feature:
1. Feature branch
2. Code Review
3. Techlead geeft Yes/No
4. If yes -> Build master merged

Voorwaarden:
- Run maven /w Sonarqube lokaal
- 100% Sonar Complient (0 issues, 100% public documentation, 0% code duplication)
- 80% Coverage on Feature (Actual logic, Model is meh)


        
## Code

__Abstractie, bijv. Repository__
    
* Abstracte Repository (al dan niet interface) met daarin koppeling naar database type
* Repository implementatie (al dan niet interface) met daarin extends naar abstracte repository, bijv:
    * CustomerRepo - findByFirstName(), findByLastName() etc.
    * ProductRepo - findByCategory() etc.

__Documentatie__

* Elke publieke methode documentatie geven voor de feature af is.
* Vage constructies inline documenteren (lambda)

## Links

* [JIRA](http://repoj:8085/jira/browse/TEAMTWO/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel)
* [Sonar](http://10.32.43.248:9000/sonar/)
* [Jenkins](http://10.32.43.249/jenkins/view/Team%202/)
* [Stash](http://10.32.43.248:7990/stash/projects)
* [Spring Backend](https://github.com/JavaMinor2015/T2C3-Spring)
* [Angular Frontend](https://github.com/JavaMinor2015/T2C3-Angular)
* [Android Frontend](https://github.com/JavaMinor2015/T2C3-Android)

## SonarQube
Linux users can run:
'mvn sonar:sonar' for maven
Kitematic users run:
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.jdbc.url="jdbc:h2:tcp://localhost/sonar"
and replace 'localhost' with their docker virtual machine ip.