# Spring Boot GitHub Rest Api Testing
### Pre-requisites
* Java SE Development Kit 8
* Maven 3.0+
### Steps to Setup
* Clone the application : git clone https://github.com/Foysal061/Api-testing.git
### Getting Started
Import the Maven project straight to your Java IDE:
* Intellij IDEA
### Explore Rest APIs
The definition of the GitHub APIs are given in the following link: 

https://docs.github.com/en/rest/guides/getting-started-with-the-rest-api

Listing the APIs below that I have used for my testing:

| Method | Url | Decription | Sample Valid Request Body |
| ------ | --- | ---------- | --------------------------- |
| POST   | https://api.github.com/user/repos | Create a repository in GitHub for the authorized user | [JSON](#signup) |
| GET   | https://api.github.com/user/repos | Get list of repositories for the authorized user| [JSON](#signin) |
| DELETE   | https://api.github.com/repos/{{userName}}/{{repoName}}| Delete the repository of the authorized user | [VOID](#signin) |

### Run all the tests
* Open a terminal in the project directory
* Execute: mvn -Dtest=GitHubRepoApiTest test
