adf_dvt_12_1_3_custom_basemap_hotspot-editor
============================================

A simple web application (ADF 12..1.3) into which you can upload an image and specify the hotspots on it. The application will generate the ADF DVT Thematic Map custom base map XML configuration file for you.

Steps to get going:

- Clone this repository or download the zip-file and expand locally
- Open the CustomBaseMapEditor.jws file in JDeveloper 12.1.3 (or higher)
- Identify a local directory that you will use for holding the image files
- Configure that local image directory in class FileHandler (public final static String imageDirectory)
- Run custom-basemap-editor.jsf
- When the page opens, upload an image, press the button Process File. The image is shown in the browser. Click on it to define hotspots; in the code editor you will find the custom base map xml required for the Thematic Map component
- 
TODO:
- set logical identifier and long label on the hotspots
- generate hotspots over a range (horizontal, vertical) or grid
- copy sets of hotspots between images
