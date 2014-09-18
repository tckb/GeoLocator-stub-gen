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
