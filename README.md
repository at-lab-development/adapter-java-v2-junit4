# JIRAJUnit4TestListener for JUnit4

JIRAJUnit4TestListener for JUnit4 is a Java library for creating proper artifact for Jenkins plugin.Required JAVA 8 or higher.

## Installation
Add rows below in pom.xml file
```xml
<repositories>
    <repository>
        <id>adapter-java-junit4-mvn-repo</id>
        <url>https://raw.github.com/EDbarvinsky/JIraJunit4/mvn-repo</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
        </repository>
....
</repositories>
```
```xml
<dependencies>
    <dependency>
        <groupId>com.epam.jira</groupId>
        <artifactId>adapter-java-junit4</artifactId>
        <version>2.0-SNAPSHOT</version>
    </dependency>
....
</dependencies>
```
## Usage
Using @RunWith on test class
```java
@RunWith(JIRAJUnit4TestRunner.class)
public class YourTestClass {
    @Test
    @JIRATestKey(key = "JIRA ticket")
    public void testMethod() {
    ....
    }
....
}
```
## Test results mapping
Test result statuses in JUnit4 is not equal for the statuses in QASpace. 
Plugin provides next way of mapping:

| JUnit4 status | QASpace status |
| ------------- | ------------- |
| Ignored  | Untested  |
| Failed  | Failed |
| Passed  | Passed  |


## Features
+ JIRATestKey annotaion
    Necessarily attribute of annotation is a key, whick must be taken from JIRA ticket.
```java
@Test
@JIRATestKey(key = "JIRA ticket")
 public void test1() {
....
}
```
   Also, there is a flag accordinaly to which value execution and preparation of test results could be skipped.

```java
@Test
@JIRATestKey(key = "JIRA ticket", disabled = true)
 public void test1() {
....
}
```
+ Attachment

   To add attachment use static method``` addAttachment()``` in ```JIRAAttachment``` class, which takes two parametrs - the first one is ```File``` object and the second is a ```String``` which should contain a JIRA ticket. After the testing procces all attachments is going to be mapped to the proper JIRA tickets and added to testresult.xml file.
    Too add attachment to the JIRA ticket add row bellow:
```java
....
JIRAAttachment.addAttachment(((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE), "test1");
....
```
## Deploying
For continuous developing and deploying create in ```.m2```  folder in your local machine ``` setting.xml```  with following credentials:
```xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>Your github login</username>
      <password>Your github password</password>
    </server>
  </servers>
</settings>
```
Also, do not forget to correct the following strings into ```pox.xml``` file if you are going to deploy library into another github repository.
```xml
<repositoryName>adapter-java-v2-junit4</repositoryName>
<repositoryOwner>at-lab-development</repositoryOwner>
```
**NOT SUPPORTED MULTITHREADING
This library include in ```pox.xml``` file JUnit4 dependency with scope marked as provided. That could cause some vulnerabilities**
