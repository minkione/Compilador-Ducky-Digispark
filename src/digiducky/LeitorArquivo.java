package digiducky;
import java.io.FileNotFoundException;
import java.io.DataInput;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;

public class LeitorArquivo {

    private DataInput arquivo = null;		// para leitura do arquivo
    private boolean usarUltimoSimbolo = false;	// para identificar que o último item deve ser usado 
    private char simboloAtual = '\0';		// item atual

    public LeitorArquivo(String fileName) throws FileNotFoundException {
        // cria objeto para leitura do arquivo de entrada
        FileInputStream fileIn = new FileInputStream(fileName);
        this.arquivo = new DataInputStream(fileIn);
    } // fim construtor

    public char getItem() throws ErroArquivo {
        if (this.usarUltimoSimbolo == false) {	// se nao eh para usar o ultimo item 		
            try {
                this.simboloAtual = (char)this.arquivo.readByte();
            } catch (EOFException e) {
                throw new ErroArquivo(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else // usar o ultimo item lido
            this.usarUltimoSimbolo = false;

        return this.simboloAtual;
    }

    public void ungetItem() {
        if (this.usarUltimoSimbolo)
            throw new RuntimeException("Soh eh possivel retornar um elemento.");

        this.usarUltimoSimbolo = true;
    }
}
