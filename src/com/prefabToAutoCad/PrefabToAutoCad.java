package com.prefabToAutoCad;

import java.util.ArrayList;
import java.util.List;

public class PrefabToAutoCad {

    List<BlocksToFill> blocksToFill;

    public PrefabToAutoCad (List<BlocksToFill> blocks) {
        this.blocksToFill = blocks;
    }

    public List<String> createAutoCadPointsList() {
        List<String> dxfStringList = new ArrayList<>();

        String initial = "999\n" +
                "DXF created from <program name>\n" +
                "0\n" +
                "SECTION\n" +
                "2\n" +
                "HEADER\n" +
                "9\n" +
                "$ACADVER\n" +
                "1\n" +
                "AC1006\n" +
                "9\n" +
                "$INSBASE\n" +
                "10\n" +
                "0.0\n" +
                "20\n" +
                "0.0\n" +
                "30\n" +
                "0.0\n" +
                "9\n" +
                "$EXTMIN\n" +
                "10\n" +
                "-100000.0\n" +
                "20\n" +
                "-100000.0\n" +
                "30\n" +
                "-100000.0\n" +
                "9\n" +
                "$EXTMAX\n" +
                "10\n" +
                "10000000000.0\n" +
                "20\n" +
                "10000000000.0\n" +
                "30\n" +
                "10000000000.0\n" +
                "0\n" +
                "ENDSEC\n" +
                "0\n" +
                "SECTION\n" +
                "2\n" +
                "ENTITIES\n";
        dxfStringList.add(initial);

        for (BlocksToFill block : blocksToFill) {
            Vector3i min = new Vector3i(block.region.min[0], block.region.min[1], block.region.min[2]);
            Vector3i extents = new Vector3i(block.region.size[0], block.region.size[1], block.region.size[2]);

            // generate faces for each block in the region
            for (int x = 1; x <= extents.x; x++) {
                for (int y = 1; y <= extents.y; y++) {
                    for (int z = 1; z <= extents.z; z++) {

                        int centerX = min.x + x - 1;
                        int centerY = min.y + y - 1;
                        int centerZ = min.z + z - 1;

                        // x y z represent the coordinates of the center
                        String frontFaceDxf = generateFrontFace(centerX, centerY, centerZ);
                        String topFaceDxf = generateTopFace(centerX, centerY, centerZ);
                        String bottomFaceDxf = generateBottomFace(centerX, centerY, centerZ);
                        String leftFaceDxf = generateLeftFace(centerX, centerY, centerZ);
                        String rightFaceDxf = generateRightFace(centerX, centerY, centerZ);
                        String backFaceDxf = generateBackFace(centerX, centerY, centerZ);

                        dxfStringList.add(frontFaceDxf);
                        dxfStringList.add(topFaceDxf);
                        dxfStringList.add(bottomFaceDxf);
                        dxfStringList.add(leftFaceDxf);
                        dxfStringList.add(rightFaceDxf);
                        dxfStringList.add(backFaceDxf);
                    }
                }
            }
        }

        dxfStringList.add("0\n");
        dxfStringList.add("ENDSEC\n");
        dxfStringList.add("0\n");
        dxfStringList.add("EOF\n");
        dxfStringList.add("\r");

        return dxfStringList;
    }

    /**
     * Generates the DXF string for the front face of a given center
     * @param x X co-ordinate of center
     * @param y Y co-ordinate of center
     * @param z Z co-ordinate of center
     * @return DXF string for the face constructed using Polyline
     */
    private String generateFrontFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x - 1, y + 1, z + 1);
        Vector3i topRightCorner = new Vector3i(x + 1, y + 1, z + 1);
        Vector3i bottomLeftCorner = new Vector3i(x - 1, y + 1, z - 1);
        Vector3i bottomRightCorner = new Vector3i(x + 1, y + 1, z - 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateTopFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x - 1, y - 1, z + 1);
        Vector3i topRightCorner = new Vector3i(x + 1, y - 1, z + 1);
        Vector3i bottomLeftCorner = new Vector3i(x - 1, y + 1, z + 1);
        Vector3i bottomRightCorner = new Vector3i(x + 1, y + 1, z + 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateBottomFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x - 1, y - 1, z - 1);
        Vector3i topRightCorner = new Vector3i(x + 1, y - 1, z - 1);
        Vector3i bottomLeftCorner = new Vector3i(x - 1, y + 1, z - 1);
        Vector3i bottomRightCorner = new Vector3i(x + 1, y + 1, z - 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateLeftFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x - 1, y + 1, z + 1);
        Vector3i topRightCorner = new Vector3i(x - 1, y - 1, z + 1);
        Vector3i bottomLeftCorner = new Vector3i(x - 1, y + 1, z - 1);
        Vector3i bottomRightCorner = new Vector3i(x - 1, y - 1, z - 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateRightFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x + 1, y + 1, z + 1);
        Vector3i topRightCorner = new Vector3i(x + 1, y - 1, z + 1);
        Vector3i bottomLeftCorner = new Vector3i(x + 1, y + 1, z - 1);
        Vector3i bottomRightCorner = new Vector3i(x + 1, y - 1, z - 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateBackFace(int x, int y, int z) {
        Vector3i topLeftCorner = new Vector3i(x - 1, y - 1, z + 1);
        Vector3i topRightCorner = new Vector3i(x + 1, y - 1, z + 1);
        Vector3i bottomLeftCorner = new Vector3i(x - 1, y - 1, z - 1);
        Vector3i bottomRightCorner = new Vector3i(x + 1, y - 1, z - 1);

        return generateDxfString(topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner);
    }

    private String generateDxfString(Vector3i topLeft, Vector3i topRight, Vector3i bottomLeft, Vector3i bottomRight) {
        StringBuilder dxf = new StringBuilder();
        dxf.append("0\n");
        dxf.append("3DFACE\n");
        dxf.append("8\n");
        dxf.append("0\n");

        dxf.append("10\n");
        dxf.append(topLeft.x).append("\n");
        dxf.append("20\n");
        dxf.append(topLeft.y).append("\n");
        dxf.append("30\n");
        dxf.append(topLeft.z).append("\n");

        dxf.append("11\n");
        dxf.append(topRight.x).append("\n");
        dxf.append("21\n");
        dxf.append(topRight.y).append("\n");
        dxf.append("31\n");
        dxf.append(topRight.z).append("\n");

        dxf.append("12\n");
        dxf.append(bottomLeft.x).append("\n");
        dxf.append("22\n");
        dxf.append(bottomLeft.y).append("\n");
        dxf.append("32\n");
        dxf.append(bottomLeft.z).append("\n");

        dxf.append("13\n");
        dxf.append(bottomRight.x).append("\n");
        dxf.append("23\n");
        dxf.append(bottomRight.y).append("\n");
        dxf.append("33\n");
        dxf.append(bottomRight.z).append("\n");

        return dxf.toString();
    }
}
