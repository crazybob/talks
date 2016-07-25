public static void copy(File file, OutputStream out) throws IOException {
  byte[] buffer = new byte[4096];
  FileInputStream in = new FileInputStream(file);
  try {
    int read;
    while ((read = in.read(buffer)) >= 0) out.write(buffer, 0, read);
  } finally {
    in.close();
  }
}