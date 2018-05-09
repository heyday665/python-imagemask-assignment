import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

class graphicsTools {
	public static int pixelMatrix[][];
	public static float greyScaleValue(int red, int green, int blue){
		int totalColor = 0;
		int temp = 0;
		int redI = 0;
		int greenI = 0;
		int blueI = 0;

		redI = red;
		greenI = green;
		blueI = blue;

		totalColor = ((21 * redI) / 100);
		totalColor = totalColor + ((72 * greenI) / 100);
		totalColor = totalColor + ((7 * blueI) / 100);

		return totalColor;
	}

	public static void main(String[] args){
		if (!(args.length==6)){
			System.out.println("\n!!! Incorrect arguments !!!\nUsage: java graphicsTools <tool> <image> <xPix1> <yPix1> <xPix2> <yPix2>\n");
			System.out.println("List of Current Tools:\n\tblacken <image> <xPix1> <yPix1> <xPix2> <yPix2>\n\tbrighten <image> <xPix1> <yPix1> <xPix2> <yPix2>\n\tdarken <image> <xPix1> <yPix1> <xPix2> <yPix2>\n\nTHE COORDINATES OF THE SECOND PIXEL MUST BE LARGER THAN THE FIRST\n");
			System.exit(1);
		}

		BufferedImage image=null, imageCopy=null;
		try {image = ImageIO.read(new File(args[1]));} catch (Exception e) {};
		//try {imageCopy = ImageIO.read(new File(args[1]));} catch (Exception e) {};
		//     Literally useless. Don't know why I did it.

		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		int img_Width = image.getWidth();
		int img_Height = image.getHeight();
		int img_Pixels = img_Width*img_Height;

		pixelMatrix = new int[img_Width*3][img_Height];
		int outputMatrix[][] = new int[img_Width*3][img_Height];

		for (int l = 0; l < (img_Width*3); l++){
			for (int why = 0; why < (img_Height); why++){
				//System.out.println("l: "+l);
				//System.out.println("w: "+why);
				pixelMatrix[l][why] = 0;
				outputMatrix[l][why] = 0;
			}
		}


		System.out.println("Image width(px): " + img_Width);
		System.out.println("Image height(px): " + img_Height);

		int targetPixels;

		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;

		int currX = 0;
		int currY = 0;

		int startingPosition = 0;
		int linesTraversed = 0;

		int t_Width = 0;
		int t_Height = 0;
		int t_TotalPixels = 0;
		int margin_Width = 0;

		switch (args[0]){
			case "blacken":
				x1 = Integer.parseInt(args[2]);
				//System.out.println(x1);
				y1 = Integer.parseInt(args[3]);
				//System.out.println(y1);
				x2 = Integer.parseInt(args[4]);
				//System.out.println(x2);
				y2 = Integer.parseInt(args[5]);
				//System.out.println(y2);

				if (x1>=x2 || y1>=y2){
					System.out.println("\nCHECK USAGE\n");
					System.exit(1);
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);
				//System.out.println(startingPosition);

				for (int i=startingPosition; i<t_TotalPixels; i++){
					pixels[(i*3)+0] = (byte) 0;
					pixels[(i*3)+1] = (byte) 0;
					pixels[(i*3)+2] = (byte) 0;

					//System.out.println(i+0);
					//System.out.println(i+1);
					//System.out.println(i+2);
					//System.out.println("\n");
					currX++;

					if(currX==x2){
						//System.out.println("Bounce");
						i = i + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}
				break;

			case "brighten":
				x1 = Integer.parseInt(args[2]);
				//System.out.println(x1);
				y1 = Integer.parseInt(args[3]);
				//System.out.println(y1);
				x2 = Integer.parseInt(args[4]);
				//System.out.println(x2);
				y2 = Integer.parseInt(args[5]);
				//System.out.println(y2);

				if (x1>=x2 || y1>=y2){
					System.out.println("\nCHECK USAGE\n");
					System.exit(1);
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);
				//System.out.println(startingPosition);

				for (int i=startingPosition; i<t_TotalPixels; i++){
					if (pixels[(i*3) + 0] >= (byte) 235) {pixels[(i*3) + 0] = (byte) 255;}
					                                else {pixels[(i*3) + 0] += 20;}
					if (pixels[(i*3) + 1] >= (byte) 235) {pixels[(i*3) + 1] = (byte) 255;}
					                                else {pixels[(i*3) + 1] += 20;}
					if (pixels[(i*3) + 2] >= (byte) 235) {pixels[(i*3) + 2] = (byte) 255;}
					                                else {pixels[(i*3) + 2] += 20;}

					//System.out.println(i+0);
					//System.out.println(i+1);
					//System.out.println(i+2);
					//System.out.println("\n");
					currX++;

					if(currX==x2){
						//System.out.println("Bounce");
						i = i + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}
				break;

			case "darken":
				x1 = Integer.parseInt(args[2]);
				//System.out.println(x1);
				y1 = Integer.parseInt(args[3]);
				//System.out.println(y1);
				x2 = Integer.parseInt(args[4]);
				//System.out.println(x2);
				y2 = Integer.parseInt(args[5]);
				//System.out.println(y2);

				if (x1>=x2 || y1>=y2){
					System.out.println("\nCHECK USAGE\n");
					System.exit(1);
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);
				//System.out.println(startingPosition);

				for (int i=startingPosition; i<t_TotalPixels; i++){
					if (pixels[(i*3) + 0] <= (byte) 20) {pixels[(i*3) + 0] = (byte) 0;}
					                               else {pixels[(i*3) + 0] -= 20;}
					if (pixels[(i*3) + 1] <= (byte) 20) {pixels[(i*3) + 1] = (byte) 0;}
					                               else {pixels[(i*3) + 1] -= 20;}
					if (pixels[(i*3) + 2] <= (byte) 20) {pixels[(i*3) + 2] = (byte) 0;}
					                               else {pixels[(i*3) + 2] -= 20;}

					//System.out.println(i+0);
					//System.out.println(i+1);
					//System.out.println(i+2);
					//System.out.println("\n");
					currX++;

					if(currX==x2){
						//System.out.println("Bounce");
						i = i + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}
				break;

			case "greyscale":
				x1 = Integer.parseInt(args[2]);
				//System.out.println(x1);
				y1 = Integer.parseInt(args[3]);
				//System.out.println(y1);
				x2 = Integer.parseInt(args[4]);
				//System.out.println(x2);
				y2 = Integer.parseInt(args[5]);
				//System.out.println(y2);

				if (x1>=x2 || y1>=y2){
					System.out.println("\nCHECK USAGE\n");
					System.exit(1);
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);
				//System.out.println(startingPosition);

				for (int i=startingPosition; i<t_TotalPixels; i++){
					int redVal = 0;
					int greenVal = 0;
					int blueVal = 0;

					blueVal  = pixels[(i*3) + 0];
					greenVal = pixels[(i*3) + 1];
					redVal   = pixels[(i*3) + 2];

					pixels[(i*3) + 0] = pixels[(i*3) + 1] = pixels[(i*3) + 2] = (byte) greyScaleValue(redVal, greenVal, blueVal);

					currX++;

					if(currX==x2){
						//System.out.println("Bounce");
						i = i + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}
				break;
			/*
			case "mask":
				x1 = Integer.parseInt(args[2]);
				//System.out.println(x1);
				y1 = Integer.parseInt(args[3]);
				//System.out.println(y1);
				x2 = Integer.parseInt(args[4]);
				//System.out.println(x2);
				y2 = Integer.parseInt(args[5]);
				//System.out.println(y2);

				if (x1>=x2 || y1>=y2){
					System.out.println("\nCHECK USAGE\n");
					System.exit(1);
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);
				//System.out.println(startingPosition);

				for (int i=startingPosition; i<t_TotalPixels; i++){
					int redVal = 0;
					int greenVal = 0;
					int blueVal = 0;

					blueVal  = pixels[(i*3) + 0];
					greenVal = pixels[(i*3) + 1];
					redVal   = pixels[(i*3) + 2];

					pixels[(i*3) + 0] = pixels[(i*3) + 1] = pixels[(i*3) + 2] = (byte) greyScaleValue(redVal, greenVal, blueVal);

					currX++;

					if(currX==x2){
						//System.out.println("Bounce");
						i = i + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}

				t_Width = x2-x1;
				//System.out.println(t_Width);
				t_Height = y2-y1;
				//System.out.println(t_Height);
				margin_Width = img_Width - t_Width;
				//System.out.println(margin_Width);
				currX = x1;
				currY = y1;

				t_TotalPixels = (t_Width*t_Height);
				//System.out.println(t_TotalPixels);

				startingPosition = ((img_Width*y1)+x1);

				int thing = img_Width*3;
				for (int j = 0; j < img_Pixels; j++){
					pixelMatrix[j%thing][linesTraversed] = pixels[j];
					if (( j % thing ) == 0){
						linesTraversed++;
					}
				}
				System.out.println(linesTraversed);

				for (int k = startingPosition+1; k<(t_TotalPixels-(3*t_Height)); k++){
					//Do Laplacian operation here.
					outputMatrix[(k*3)%thing][currY+1] = maskOperation(((k*3)%thing)+1, currY+1);

					currX++;
					if(currX==x2){
						//System.out.println("Bounce");
						k = k + margin_Width;
						t_TotalPixels = t_TotalPixels+margin_Width;
						currX = x1;
						currY++;
					}
				}

				for (int l = 0; l < (img_Height); l++){
					for (int why = 0; why < (img_Width*3); why++){
						//System.out.println("l: "+l);
						//System.out.println("w: "+why);
						//pixelMatrix[l][why] = 0;
						//outputMatrix[l][why] = 0;
						int pixLocation = (why+(l*img_Width));
						//System.out.println(pixLocation);
						pixels[pixLocation] = 0;
						pixels[pixLocation] += outputMatrix[why][l];
						pixels[pixLocation+1] = pixels[pixLocation];
						//if (pixLocation >= 135723900){
							//System.out.println("\n"+ why + "  " + l +"\n");
						//}
						pixels[pixLocation+2] = pixels[pixLocation];
					}
				}
				break;
			*/

				//Mask function here is deprecated, as this program has turned into about 90% duct tape,
				// and the duct tape is leaking some where, and I can't be assed to go cover duct tape
				// with more duct tape that will be likely to leak in a few days...
				
			}
		try {boolean didWork = ImageIO.write(image, "jpg", new File("output.jpg"));
			System.out.println(didWork);} catch (Exception e) {e.printStackTrace();};
	}

	public static int maskOperation(int x, int y){
		int total = 0;
		total += (pixelMatrix[x-1][y] * -1);
		total += (pixelMatrix[x+1][y] * -1);
		total += (pixelMatrix[x][y+1] * -1);
		total += (pixelMatrix[x][y-1] * -1);
		total += (pixelMatrix[x][y] * 4);
		total += (pixelMatrix[x-1][y-1] * 0);
		total += (pixelMatrix[x-1][y+1] * 0);
		total += (pixelMatrix[x+1][y-1] * 0);
		total += (pixelMatrix[x+1][y+1] * 0);
		return total;
	}
}

/* HOW TO USE */
/*
	-Get an image
	-Run java graphicsTools <tool> <x1> <y1> <x2> <y2>
		-tool =    operations to use (black or bright)
		-x1   =    x co-ord of top left pixel of the selection box
		-y1   =    y co-ord of top left pixel of the selection box
		-x2   =    x co-ord of the bottom right pixel of the selection box
		-y2   =    y co-ord of the bottom right pixel of the selection box
*/

// Rick:
/* *********************************************** */
// Something is horribly wrong with my for loops,  //
//      as in, I counts ALL integers I pass by,    //
//   as opposed to only the integers I intend to   // 
//                 operate on.                     //
//												   //
//  I need to find a convenient way to use currX,  //
// as opposed to using I, as that would stop the   //
// early stopping. Perhaps I could use a 'skipped' //
// variable and add that to the pixel position I   //
//                  operate on.                    //
/* *********************************************** */