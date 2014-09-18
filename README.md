<<<<<<< HEAD
GeoLocator-stub-generator
=========================

Use this client to generate the stubs that are neccessary for the REST Api service client. The binaries are located in the <code>bin</code> folder. 

Usage
------

<code>
java -jar stub-generator.jar [api_url_base] [out_dir] <br/>

api_url_base: The base url of the REST service <br/>
our_dir: path to the directory where the stubs are generated<br/>
</code>
<br/>
NB:
Both the parameters are optional. By default, the base url is set to the live version and the stubs are generated in the same directory as the jar file. 
=======
GeoLocator-REST-API
===================

The Java-REST API for GeoLocator webservice  compatible to both Java and Android platforms. 

--

Before using the API, it is *strongly* adviced to use the stub generator to generate the stubs located in  <code>com/tckb/geo/stubs</code> to keep consistent to the changes in the rest webservice. The usage is pretty straight forward, the main API is located at <code>com/tckb/geo/api/LocatorREST</code> 
>>>>>>> origin/master
