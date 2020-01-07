




### mysql
 
```java
spring.datasource.url=jdbc:mysql://localhost:3306/javaspringclub?serverTimezone=UTC
```

### movies.sql

-  /src/main/resources/static/sql/movies.sql

```sql 
CREATE TABLE movies (
  movie_id BIGINT(5) NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  category VARCHAR(45) NULL,
  PRIMARY KEY (movie_id));
  
  
INSERT INTO movies(movie_id, title, category) VALUES
  (101, 'Mama mia', 'drama'),
  (102, 'Mission Impossible', 'action'),
  (103, 'Coco', 'animation');
```  

###  호출 

- http://localhost:8080/ws/movies.wsdl


```xml 
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:sch="http://www.javaspringclub.com/movies-ws" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://www.javaspringclub.com/movies-ws" targetNamespace="http://www.javaspringclub.com/movies-ws">
<wsdl:types>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified" targetNamespace="http://www.javaspringclub.com/movies-ws">
<xs:element name="getMovieByIdRequest">
<xs:complexType>
<xs:sequence>
<xs:element name="movieId" type="xs:long"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="getMovieByIdResponse">
<xs:complexType>
<xs:sequence>
<xs:element name="movieType" type="tns:movieType"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:complexType name="movieType">
<xs:sequence>
<xs:element name="movieId" type="xs:long"/>
<xs:element name="title" type="xs:string"/>
<xs:element name="category" type="xs:string"/>
</xs:sequence>
</xs:complexType>
<xs:element name="getAllMoviesRequest">
<xs:complexType/>
</xs:element>
<xs:element name="getAllMoviesResponse">
<xs:complexType>
<xs:sequence>
<xs:element maxOccurs="unbounded" name="movieType" type="tns:movieType"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:complexType name="serviceStatus">
<xs:sequence>
<xs:element name="statusCode" type="xs:string"/>
<xs:element name="message" type="xs:string"/>
</xs:sequence>
</xs:complexType>
<xs:element name="addMovieRequest">
<xs:complexType>
<xs:sequence>
<xs:element name="title" type="xs:string"/>
<xs:element name="category" type="xs:string"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="addMovieResponse">
<xs:complexType>
<xs:sequence>
<xs:element name="serviceStatus" type="tns:serviceStatus"/>
<xs:element name="movieType" type="tns:movieType"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="updateMovieRequest">
<xs:complexType>
<xs:sequence>
<xs:element name="title" type="xs:string"/>
<xs:element name="category" type="xs:string"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="updateMovieResponse">
<xs:complexType>
<xs:sequence>
<xs:element name="serviceStatus" type="tns:serviceStatus"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="deleteMovieRequest">
<xs:complexType>
<xs:sequence>
<xs:element name="movieId" type="xs:long"/>
</xs:sequence>
</xs:complexType>
</xs:element>
<xs:element name="deleteMovieResponse">
<xs:complexType>
<xs:sequence>
<xs:element name="serviceStatus" type="tns:serviceStatus"/>
</xs:sequence>
</xs:complexType>
</xs:element>
</xs:schema>
</wsdl:types>
<wsdl:message name="deleteMovieResponse">
<wsdl:part element="tns:deleteMovieResponse" name="deleteMovieResponse"> </wsdl:part>
</wsdl:message>
<wsdl:message name="getMovieByIdResponse">
<wsdl:part element="tns:getMovieByIdResponse" name="getMovieByIdResponse"> </wsdl:part>
</wsdl:message>
<wsdl:message name="addMovieResponse">
<wsdl:part element="tns:addMovieResponse" name="addMovieResponse"> </wsdl:part>
</wsdl:message>
<wsdl:message name="getAllMoviesRequest">
<wsdl:part element="tns:getAllMoviesRequest" name="getAllMoviesRequest"> </wsdl:part>
</wsdl:message>
<wsdl:message name="addMovieRequest">
<wsdl:part element="tns:addMovieRequest" name="addMovieRequest"> </wsdl:part>
</wsdl:message>
<wsdl:message name="updateMovieRequest">
<wsdl:part element="tns:updateMovieRequest" name="updateMovieRequest"> </wsdl:part>
</wsdl:message>
<wsdl:message name="deleteMovieRequest">
<wsdl:part element="tns:deleteMovieRequest" name="deleteMovieRequest"> </wsdl:part>
</wsdl:message>
<wsdl:message name="getAllMoviesResponse">
<wsdl:part element="tns:getAllMoviesResponse" name="getAllMoviesResponse"> </wsdl:part>
</wsdl:message>
<wsdl:message name="getMovieByIdRequest">
<wsdl:part element="tns:getMovieByIdRequest" name="getMovieByIdRequest"> </wsdl:part>
</wsdl:message>
<wsdl:message name="updateMovieResponse">
<wsdl:part element="tns:updateMovieResponse" name="updateMovieResponse"> </wsdl:part>
</wsdl:message>
<wsdl:portType name="MoviesPort">
<wsdl:operation name="deleteMovie">
<wsdl:input message="tns:deleteMovieRequest" name="deleteMovieRequest"> </wsdl:input>
<wsdl:output message="tns:deleteMovieResponse" name="deleteMovieResponse"> </wsdl:output>
</wsdl:operation>
<wsdl:operation name="getMovieById">
<wsdl:input message="tns:getMovieByIdRequest" name="getMovieByIdRequest"> </wsdl:input>
<wsdl:output message="tns:getMovieByIdResponse" name="getMovieByIdResponse"> </wsdl:output>
</wsdl:operation>
<wsdl:operation name="addMovie">
<wsdl:input message="tns:addMovieRequest" name="addMovieRequest"> </wsdl:input>
<wsdl:output message="tns:addMovieResponse" name="addMovieResponse"> </wsdl:output>
</wsdl:operation>
<wsdl:operation name="getAllMovies">
<wsdl:input message="tns:getAllMoviesRequest" name="getAllMoviesRequest"> </wsdl:input>
<wsdl:output message="tns:getAllMoviesResponse" name="getAllMoviesResponse"> </wsdl:output>
</wsdl:operation>
<wsdl:operation name="updateMovie">
<wsdl:input message="tns:updateMovieRequest" name="updateMovieRequest"> </wsdl:input>
<wsdl:output message="tns:updateMovieResponse" name="updateMovieResponse"> </wsdl:output>
</wsdl:operation>
</wsdl:portType>
<wsdl:binding name="MoviesPortSoap11" type="tns:MoviesPort">
<soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
<wsdl:operation name="deleteMovie">
<soap:operation soapAction=""/>
<wsdl:input name="deleteMovieRequest">
<soap:body use="literal"/>
</wsdl:input>
<wsdl:output name="deleteMovieResponse">
<soap:body use="literal"/>
</wsdl:output>
</wsdl:operation>
<wsdl:operation name="getMovieById">
<soap:operation soapAction=""/>
<wsdl:input name="getMovieByIdRequest">
<soap:body use="literal"/>
</wsdl:input>
<wsdl:output name="getMovieByIdResponse">
<soap:body use="literal"/>
</wsdl:output>
</wsdl:operation>
<wsdl:operation name="addMovie">
<soap:operation soapAction=""/>
<wsdl:input name="addMovieRequest">
<soap:body use="literal"/>
</wsdl:input>
<wsdl:output name="addMovieResponse">
<soap:body use="literal"/>
</wsdl:output>
</wsdl:operation>
<wsdl:operation name="getAllMovies">
<soap:operation soapAction=""/>
<wsdl:input name="getAllMoviesRequest">
<soap:body use="literal"/>
</wsdl:input>
<wsdl:output name="getAllMoviesResponse">
<soap:body use="literal"/>
</wsdl:output>
</wsdl:operation>
<wsdl:operation name="updateMovie">
<soap:operation soapAction=""/>
<wsdl:input name="updateMovieRequest">
<soap:body use="literal"/>
</wsdl:input>
<wsdl:output name="updateMovieResponse">
<soap:body use="literal"/>
</wsdl:output>
</wsdl:operation>
</wsdl:binding>
<wsdl:service name="MoviesPortService">
<wsdl:port binding="tns:MoviesPortSoap11" name="MoviesPortSoap11">
<soap:address location="http://localhost:8080/ws"/>
</wsdl:port>
</wsdl:service>
</wsdl:definitions>
```