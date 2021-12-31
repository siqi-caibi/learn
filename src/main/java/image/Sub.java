package image;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Sub {
    public static final File f3=new File("C:\\Users\\EDZ\\Documents\\630海报\\630海报\\6.png");
    public static final List<File> f2=new ArrayList<>();
    {
        f2.add(f3);
    }
    public static final int captchaInterferenceOptions=1;
    public static void main(String[] args) throws Exception{
        File image=new File("C:\\Users\\EDZ\\Documents\\630海报\\630海报\\floatPic.jpg");
        BufferedImage originalImage = ImageIO.read(image);
        //设置水印
        Graphics backgroundGraphics = originalImage.getGraphics();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        backgroundGraphics.setFont(new Font("宋体",Font.ITALIC,45));
        backgroundGraphics.setColor(Color.MAGENTA);
        backgroundGraphics.drawString("水印大大大", 10, 40);



        File jimage=new File("C:\\Users\\EDZ\\Documents\\630海报\\630海报\\16.png");
        BufferedImage jigsawImage = ImageIO.read(jimage);

        pictureTemplatesCut(originalImage, jigsawImage );
    }

    /**
     * 根据模板切图
     * 记录 point和对应 key
     * @throws Exception
     */
    public static void pictureTemplatesCut(BufferedImage originalImage, BufferedImage jigsawImage) throws IOException {

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int jigsawWidth = jigsawImage.getWidth();
        int jigsawHeight = jigsawImage.getHeight();
        System.out.println(originalWidth+"-------"+originalHeight+"-------"+jigsawHeight+"-------"+jigsawWidth);
        //随机生成拼图坐标
        Map<String,Integer> point = generateJigsawPoint(originalWidth, originalHeight, jigsawWidth, jigsawHeight);
        int x = point.get("x");
        int y = point.get("y");
        System.out.println(point);

        //生成新的拼图图像
        BufferedImage newJigsawImage = new BufferedImage(jigsawWidth, jigsawHeight, jigsawImage.getType());
        Graphics2D graphics = newJigsawImage.createGraphics();

        // 新建的图像根据模板颜色赋值,源图生成遮罩
        cutByTemplate(originalImage, jigsawImage, newJigsawImage, x, 0);

        if (captchaInterferenceOptions > 0) {
            int position = 0;
            if (originalWidth - x - 5 > jigsawWidth * 2) {
                //在原扣图右边插入干扰图
                position = getRandomInt(x + jigsawWidth + 5, originalWidth - jigsawWidth);
            } else {
                //在原扣图左边插入干扰图
                position = getRandomInt(100, x - jigsawWidth - 5);
            }
            interferenceByTemplate(originalImage,ImageIO.read(f3), position, 0);
        }
        if (captchaInterferenceOptions > 1) {
            Integer randomInt = getRandomInt(jigsawWidth, 100 - jigsawWidth);
            interferenceByTemplate(originalImage, ImageIO.read(f3), randomInt, 0);
        }

        // 设置“抗锯齿”的属性
        int bold = 5;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newJigsawImage, 0, 0, null);
        graphics.dispose();


        File uuo=new File("C:\\Users\\EDZ\\Documents\\630海报\\630海报\\oo.jpg");
        ImageIO.write(originalImage, "png", uuo);

        File rro=new File("C:\\Users\\EDZ\\Documents\\630海报\\630海报\\rr.jpg");
        ImageIO.write(newJigsawImage, "png", rro);

        System.out.println("记录 point和对应 key");
    }

    /**
     * 随机范围内数字
     * @param startNum
     * @param endNum
     * @return
     */
    public static Integer getRandomInt(int startNum, int endNum) {
        return ThreadLocalRandom.current().nextInt(endNum-startNum) + startNum;
    }

    /**
     * 随机生成拼图坐标
     *
     * @param originalWidth
     * @param originalHeight
     * @param jigsawWidth
     * @param jigsawHeight
     * @return
     */
    private static Map<String,Integer> generateJigsawPoint(int originalWidth, int originalHeight, int jigsawWidth, int jigsawHeight) {
        Random random = new Random();
        int widthDifference = originalWidth - jigsawWidth;
        int heightDifference = originalHeight - jigsawHeight;
        int x, y = 0;
        if (widthDifference <= 0) {
            x = 5;
        } else {
            x = random.nextInt(originalWidth - jigsawWidth - 100) + 100;
        }
        if (heightDifference <= 0) {
            y = 5;
        } else {
            y = random.nextInt(originalHeight - jigsawHeight) + 5;
        }
        Map<String,Integer> res=new HashMap<>(2);
        res.put("x",x);
        res.put("y",y);
        return res;
    }


    /**
     * @param oriImage      原图
     * @param templateImage 模板图
     * @param newImage      新抠出的小图
     * @param x             随机扣取坐标X
     * @param y             随机扣取坐标y
     * @throws Exception
     */
    private static void cutByTemplate(BufferedImage oriImage, BufferedImage templateImage, BufferedImage newImage, int x, int y) {
        //临时数组遍历用于高斯模糊存周边像素值
        int[][] martrix = new int[3][3];
        int[] values = new int[9];

        int xLength = templateImage.getWidth();
        int yLength = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < xLength; i++) {
            // 模板图片高度
            for (int j = 0; j < yLength; j++) {
                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int rgb = templateImage.getRGB(i, j);
                if (rgb < 0) {
                    newImage.setRGB(i, j, oriImage.getRGB(x + i, y + j));

                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, y + j, values);
                    fillMatrix(martrix, values);
                    oriImage.setRGB(x + i, y + j, avgMatrix(martrix));
                }

                //防止数组越界判断
                if (i == (xLength - 1) || j == (yLength - 1)) {
                    continue;
                }
                int rightRgb = templateImage.getRGB(i + 1, j);
                int downRgb = templateImage.getRGB(i, j + 1);
                //描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    newImage.setRGB(i, j, Color.white.getRGB());
                    oriImage.setRGB(x + i, y + j, Color.white.getRGB());
                }
            }
        }

    }

    /**
     * 干扰抠图处理
     *
     * @param oriImage      原图
     * @param templateImage 模板图
     * @param x             随机扣取坐标X
     * @param y             随机扣取坐标y
     * @throws Exception
     */
    private static void interferenceByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x, int y) {
        //临时数组遍历用于高斯模糊存周边像素值
        int[][] martrix = new int[3][3];
        int[] values = new int[9];

        int xLength = templateImage.getWidth();
        int yLength = templateImage.getHeight();
        // 模板图像宽度
        for (int i = 0; i < xLength; i++) {
            // 模板图片高度
            for (int j = 0; j < yLength; j++) {
                // 如果模板图像当前像素点不是透明色 copy源文件信息到目标图片中
                int rgb = templateImage.getRGB(i, j);
                if (rgb < 0) {
                    //抠图区域高斯模糊
                    readPixel(oriImage, x + i, y + j, values);
                    fillMatrix(martrix, values);
                    oriImage.setRGB(x + i, y + j, avgMatrix(martrix));
                }
                //防止数组越界判断
                if (i == (xLength - 1) || j == (yLength - 1)) {
                    continue;
                }
                int rightRgb = templateImage.getRGB(i + 1, j);
                int downRgb = templateImage.getRGB(i, j + 1);
                //描边处理，,取带像素和无像素的界点，判断该点是不是临界轮廓点,如果是设置该坐标像素是白色
                if ((rgb >= 0 && rightRgb < 0) || (rgb < 0 && rightRgb >= 0) || (rgb >= 0 && downRgb < 0) || (rgb < 0 && downRgb >= 0)) {
                    oriImage.setRGB(x + i, y + j, Color.white.getRGB());
                }
            }
        }

    }

    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;
        for (int i = xStart; i < 3 + xStart; i++) {
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;

                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty);

            }
        }
    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }
        return new Color(r / 8, g / 8, b / 8).getRGB();
    }
}
