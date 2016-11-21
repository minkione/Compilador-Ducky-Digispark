
//DELAY 750;GUI r;DELAY 1000;STRING powershell Start-Process notepad -Verb runAs ;DELAY 750;ALT y;DELAY 750;ALT SPACE;DELAY 1000;STRING m;DELAY 1000;REPLAY 10;

bool digi = false;
String args = "";  
String cmd = "";    
String ultArgs = "";  
String ultCmd = "";   

void setup() {
  // initialize serial:
  Serial.begin(9600);
  // reserve 200 bytes for the inputString:
}

void loop() {
  while(Serial.available()){
    proxComando();
    /*Serial.println("Comando:" + cmd);
    Serial.println("Argumento:" + args);*/
    Serial.println(getComando(cmd, args));
  }
  Serial.flush();
}

String getComando(String comando, String argumento){
  if(comando == "REM");
  else if(comando == "DELAY"){
    return("DigiKeyboard.delay("+argumento+");");   
  }
  else if (comando == "STRING"){
    return("DigiKeyboard.print(\""+argumento+"\"); ");
  }
  else if (comando == "ALT"){
    argumento.toUpperCase();
    if(argumento != "")return("DigiKeyboard.sendKeyStroke(KEY_"+argumento+",MOD_ALT_LEFT);");
    else  return("DigiKeyboard.sendKeyStroke(MOD_ALT_LEFT);");
  }
  else if (comando == "CONTROL" || comando == "CTRL"){
    argumento.toUpperCase();
    if(argumento != "")return("DigiKeyboard.sendKeyStroke(KEY_"+argumento+",MOD_CONTROL_LEFT);");
    else  return("DigiKeyboard.sendKeyStroke(MOD_CONTROL_LEFT );");
  }
  else if (comando == "GUI" || comando == "WINDOWS"){
    argumento.toUpperCase();
    if(argumento != "")return("DigiKeyboard.sendKeyStroke(KEY_"+argumento+",MOD_GUI_LEFT);");
    else  return("DigiKeyboard.sendKeyStroke(MOD_GUI_LEFT);");
  }
 else if (comando == "SHIFT"){
    argumento.toUpperCase();
    if(argumento != "")return("DigiKeyboard.sendKeyStroke(KEY_"+argumento+",MOD_SHIFT_LEFT);");
    else  return("DigiKeyboard.sendKeyStroke(MOD_SHIFT_LEFT);");
  }
  else if (comando == "MENU" || comando == "APP"){
    return("DigiKeyboard.sendKeyStroke(KEY_F10,MOD_SHIFT_LEFT);");
  }
  else if (comando == "DEFAULT-DELAY" || comando == "DEFAULTDELAY"){
    
  }
  else if (comando == "REPLAY"){
    return("for(int i=0;i<"+argumento+";i++)" + getComando(ultCmd,ultArgs)); 
  }
}

bool proxComando() {
  if(Serial.available()) {
    String in = Serial.readStringUntil(' ');
    ultCmd = cmd;
    cmd = in;
    
    String arg = Serial.readStringUntil(';');
    ultArgs = args;
    args = arg;
    return true;
  }
  else return false;
}

void trava(){
  //if(digi) DigiKeyboard.println("ERRO DE EXECUÇÃO");
  while(true);
}


