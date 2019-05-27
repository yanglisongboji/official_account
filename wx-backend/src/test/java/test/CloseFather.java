package test;
import java.io.IOException;

public class CloseFather implements AutoCloseable {

	@Override
	public void close() throws IOException {
		System.out.println("111");
	}

}
