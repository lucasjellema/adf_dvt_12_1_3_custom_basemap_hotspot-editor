 var hotspotColor ='green';
 var hotspotRadius = 15; 
 var imageWidth =0;
 var imageHeight =0;
 var hotspots = new Array();
 var imageFileName = "";
 
 
 
 function getContext() {
    var canvas = document.getElementById("canvas1");
    var ctx = canvas.getContext("2d");
    return ctx;
 }
 
 function newCanvasListen() {
   var canvas = document.getElementById("canvas1");

   canvas.addEventListener("mousedown", doMouseDown, false);
   refreshCustomBasemapXML();
 }
    
 function setCanvasImage(fileName, width, height) {
    getContext().clearRect(0,0, imageWidth,imageHeight);
    imageWidth = width;
    imageHeight = height;
    imageFileName = fileName;
    var imgBasket = new Image();
    imgBasket.src = "images/"+imageFileName;
    imgBasket.id = "imgBasket";
 
    imgBasket.onload = function() {
      var imageBasket = getContext().drawImage(imgBasket, 0, 0);
      getContext().fillStyle = imageBasket;
    };
    hotspots = new Array();
    refreshCustomBasemapXML();

}
       
 function doMouseDown(e) {
    var mouseX = e.pageX - this.offsetLeft;
    var mouseY = e.pageY - this.offsetTop;
    
    hotspots.push({ x:mouseX, y:mouseY,name:'newHotSpot', label:''});   
    refreshCustomBasemapXML();
    drawHotspot(mouseX, mouseY, hotspots.length);
 }
       
       
 function drawHotspot(x,y, id){
   var context = getContext();
   var centerX = x;
   var centerY = y;
   context.beginPath();
   context.arc(centerX, centerY, hotspotRadius, 0, 2 * Math.PI, false);
   context.fillStyle = hotspotColor;
   context.fill();
   context.lineWidth = 5;
   context.strokeStyle = '#003300';
   context.stroke();  
   context.font = '8pt Calibri';
   context.fillStyle = 'white';
   context.textAlign = 'center';
   context.fillText(id, x, y+3);
 }


 function refreshCustomBasemapXML() {
   var editor = AdfPage.PAGE.findComponent('xmlEditor');
   editor.setValue(generateCustomBasemapXML());
 }


 function generateCustomBasemapXML() {
    var xml = '';
    xml = "<basemap id='yourBaseMap'>\n";
    xml=xml+"  <layer id='layer1'>\n"; 
    xml=xml+"    <image height='"+imageHeight+"' width='"+imageWidth+"' source='/images/"+imageFileName+"'/>\n";
    xml=xml+"  </layer>\n";
    xml=xml+"  <points>\n";
    var arrayLength = hotspots.length;
    for (var i = 0; i < arrayLength; i++) {
      xml = xml + "    <point x='"+hotspots[i].x+"' y='"+hotspots[i].y+"' name='"+(i+1)+"' longLabel=''/>\n";
    }//for
    xml=xml+"  </points>\n";
    xml=xml+"</basemap>\n";
    return xml;
}
