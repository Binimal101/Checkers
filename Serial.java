import java.io.*;

public class Serial<T> {	
	T object;
	public Serial(T object) {
		this.object = object;
	}

	public Serial() {
		
	}
	
	public void serialize(String path) {
		try {
        	FileOutputStream fileOut = new FileOutputStream("saved/" + path + ".ser");
        	ObjectOutputStream out = new ObjectOutputStream(fileOut);
        	out.writeObject(object);
			
         	out.close();
			
         	fileOut.close();
			
      	} catch (IOException i) {
        	i.printStackTrace();
      	}
	}

	public T deserialize(String path) {
		try {
        	FileInputStream fileIn = new FileInputStream("saved/" + path + ".ser");
         	ObjectInputStream in = new ObjectInputStream(fileIn);
			
         	object = (T) in.readObject();
			
			return object;
			
      	} catch (IOException i) {
        	i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
		}
		
		return null;
	}
}