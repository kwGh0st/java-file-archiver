package archiver.filearchiver.files;

public class FileProperties {
    private final String name;
    private final long size;
    private final long compressedSize;

    public FileProperties(String name, long size, long compressedSize) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
    }



    public long getCompressionRatio() {
        return 100 - ((compressedSize * 100) / size);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("File name: ");
        builder.append(name);
        if (size > 0) {
            builder.append(" Normal size: ");
            builder.append(size / 1024);
            builder.append(" KB (");
            builder.append(" Compressed Size: ");
            builder.append(compressedSize / 1024);
            builder.append(" KB) compression: ");
            builder.append(getCompressionRatio());
            builder.append("%");
        }

        return builder.toString();
    }

}
