package digiducky;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class DigiDucky {

    public static ArrayList<String> cmds = new ArrayList();
    public static ArrayList<String> stack = new ArrayList();
    static int pointer = 0;
    public static int getComando(String comando, boolean bool) {
        if (comando.equals("REM")){
            return 1;
        } else if (comando.equals("DELAY")) {
            System.out.println("DELAY " + stack.get(pointer));
            pointer++;
            return 2;
        } else if (comando.equals("STRING")) {
            System.out.println("STRING " + stack.get(pointer));
            pointer++;
            return 3;
        } else if (comando.equals("ALT")) {
           System.out.println("ALT " + stack.get(pointer));
           pointer++; 
           return 4;
        } else if (comando.equals("CONTROL") || comando.equals("CTRL")) {
            System.out.println("CONTROL " + stack.get(pointer));
            pointer++;
            return 5;
        } else if (comando.equals("GUI") || comando.equals("WINDOWS")) {
            System.out.println("GUI " + stack.get(pointer));
            pointer++;
            return 6;
        } else if (comando.equals("SHIFT")) {
            System.out.println("SHIFT " + stack.get(pointer));
            pointer++;
            return 7;
        } else if (comando.equals("MENU") || comando.equals("APP")) {
            System.out.println("MENU");
            return 8;
        } else if (comando.equals("DEFAULT-DELAY") || comando.equals("DEFAULTDELAY")) {
            System.out.println("DEFAULT-DELAY " + stack.get(pointer));
            pointer++;
            return 9;
        } else if (comando.equals("REPEAT")) {
            System.out.println("REPEAT " + stack.get(pointer));
            pointer++;
            return 10;
        }else if(comando.equals("ENTER")){
            System.out.println("ENTER");
            return 11;
        }else if (comando.equals("DOWNARROW") || comando.equals("DOWN")) {
            System.out.println("DOWNARROW");
            return 12;
        }else if (comando.equals("LEFTARROW") || comando.equals("LEFT")) {
            System.out.println("LEFTARROW");
            return 13;
        }else if (comando.equals("RIGHTARROW") || comando.equals("RIGHT")) {
            System.out.println("RIGHTARROW");
            return 14;
        }else if (comando.equals("UPARROW") || comando.equals("UP")) {
            System.out.println("UPARROW");
            return 15;
        }else if (comando.equals("ESCAPE") || comando.equals("ESC")) {
            System.out.println("ESC");
            return 16;
        }else if (comando.equals("PRINTSCREEN")) {
            System.out.println("PRINTSCREEN");
            return 17;
        }else if (comando.equals("TAB")) {
            System.out.println("TAB");
            return 18;
        }else if (comando.equals("SPACE")) {
            System.out.println("SPACE");
            return 19;
        }else return -1;
    }
    public static int getComando(String comando) {
        if (comando.equals("REM")){
            return 1;
        } else if (comando.equals("DELAY")) {
            return 2;
        } else if (comando.equals("STRING")) {
            return 3;
        } else if (comando.equals("ALT")) {
           return 4;
        } else if (comando.equals("CONTROL") || comando.equals("CTRL")) {
            return 5;
        } else if (comando.equals("GUI") || comando.equals("WINDOWS")) {
            return 6;
        } else if (comando.equals("SHIFT")) {
            return 7;
        } else if (comando.equals("MENU") || comando.equals("APP")) {
            return 8;
        } else if (comando.equals("DEFAULT-DELAY") || comando.equals("DEFAULTDELAY")) {
            return 9;
        } else if (comando.equals("REPEAT")) {
            return 10;
        }else if(comando.equals("ENTER")){
            return 11;
        }else if (comando.equals("DOWNARROW") || comando.equals("DOWN")) {
            return 12;
        }else if (comando.equals("LEFTARROW") || comando.equals("LEFT")) {
            return 13;
        }else if (comando.equals("RIGHTARROW") || comando.equals("RIGHT")) {
            return 14;
        }else if (comando.equals("UPARROW") || comando.equals("UP")) {
            return 15;
        }else if (comando.equals("ESCAPE") || comando.equals("ESC")) {
            return 16;
        }else if (comando.equals("PRINTSCREEN")) {
            return 17;
        }else if (comando.equals("TAB")) {
            return 18;
        }else if (comando.equals("SPACE")) {
            return 19;
        }else return -1;
    }

    public static void main(String[] args) throws FileNotFoundException, ErroArquivo, IOException {
        int index = 0;
        
        LeitorArquivo leitor = new LeitorArquivo(args[0]);
        try {
            Files.write(Paths.get(args[0]), "\nEND".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        char atual = leitor.getItem();
        while (cmds.isEmpty() || !cmds.get(cmds.size() - 1).equals("END")) {
            boolean foi = false;
            String cmd = "";
            String arg = "";
            while (atual != '\n' && atual != '\r' && (cmds.isEmpty() || !cmds.get(cmds.size() - 1).equals("END"))) {
                while (atual != ' ' && !foi && atual != '\r' && atual != '\n') {
                    cmd += atual;
                    if (!cmd.equals("END")) {
                        atual = leitor.getItem();
                    } else {
                        break;
                    }
                }
                if (!foi) {
                    cmds.add(cmd);
                    index++;
                }
                foi = true;
                if (atual != '\n' && !cmd.equals("END")) {
                    atual = leitor.getItem();
                }
                if (atual != '\n' && !cmd.equals("END")) {
                    if(atual != 0x20 && cmd.equals("STRING"))arg += atual;
                    else if(atual != 0x20) arg += atual;
                } else {
                    if (!arg.equals("") && !cmds.get(cmds.size() - 1).equals("REM")) {
                        if (arg != null && arg.length() > 0) {
                            //arg = arg.substring(0, arg.length()-1);
                        }
                        stack.add(arg);
                    }
                }
            }
            if (cmds.isEmpty() || !cmds.get(cmds.size() - 1).equals("END")) {
                atual = leitor.getItem();
            }
        }
        DataOutputStream os = new DataOutputStream(new FileOutputStream(".//inject.bin"));
        os.writeByte(stack.size());
        for (int i = 0; i < stack.size(); i++) {
            if(!stack.get(i).isEmpty())os.writeBytes(stack.get(i));
            if(!stack.get(i).isEmpty())os.writeByte(';');
        }
        os.writeChar(0x0a);
        for (int i = 0; i < cmds.size(); i++) {
            //System.out.println(getComando(cmds.get(i)));
            if(getComando(cmds.get(i)) != -1 && getComando(cmds.get(i)) != 1)os.writeByte(getComando(cmds.get(i),true));
        }
    }

}
