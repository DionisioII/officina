import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class JOutput extends JTextArea {
	
private PrintStream printStream;

  public JOutput() {
    printStream = new PrintStream(new ConsolePrintStream());
  }

  public PrintStream getPrintStream() {
    return printStream;
  }

  
  private class ConsolePrintStream extends ByteArrayOutputStream {
    public synchronized void write(byte[] b, int off, int len) {
      setCaretPosition(getDocument().getLength());
      String str = new String(b);
      append(str.substring(off, len));
    }
  }

}