/**
 * Created by Legolas on 7/9/2016.
 */

import android.graphics.Bitmap;

public class ImageProcess {
    public ImageProcess() {
        // TODO Auto-generated constructor stub
    }
    public Bitmap gray(Bitmap myBitmap)
    {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pix = new int[width * height];
        myBitmap.getPixels(pix, 0, width, 0, 0, width, height);

        // Apply pixel-by-pixel change
        int index = 0;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;
                //Log.e("red", "old red: " + r);
                r = (r + g + b)/3;
                g = (r + g + b)/3;;
                b = (r + g + b)/3;;
                pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
                index++;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        myBitmap = null;
        pix = null;
        return bitmap;
    }

    public Bitmap prewitt(Bitmap myBitmap){
        //toGray();//灰度化
        // Create new array
        int w = myBitmap.getWidth();
        int h = myBitmap.getHeight();
        int[] data = new int[w * h];
        myBitmap.getPixels(data, 0, w, 0, 0, w, h);
        int[] d= new int[w*h];
        for(int j=1;j<h-1;j++){
            for(int i=1;i<w-1;i++){
                int s1 = data[i-1+(j+1)*w]+data[i+(j+1)*w]+data[i+1+(j+1)*w]-data[i-1+(j-1)*w]-data[i+(j-1)*w]-data[i+1+(j-1)*w];
                int s2 = data[i+1+(j-1)*w]+data[i+1+(j)*w]+data[i+1+(j+1)*w]-data[i-1+(j-1)*w]-data[i-1+(j)*w]-data[i-1+(j+1)*w];
                int s  = Math.abs(s1)+Math.abs(s2);
                if(s < 0)
                    s =0;
                if(s > 255)
                    s = 255;
                d[i + j * w] = s;
            }
        }
        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        bitmap.setPixels(d, 0, w, 0, 0, w, h);
        myBitmap = null;
        d = null;
        return bitmap;
    }

    // filterWidth and filterHeight must be odd numbers
    public Bitmap averageFilter(int filterWidth, int filterHeight,Bitmap myBitmap)
    {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pixNew = new int[width * height];
        int[] pixOld = new int[width * height];
        myBitmap.getPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap.getPixels(pixOld, 0, width, 0, 0, width, height);

        // Apply pixel-by-pixel change
        int filterHalfWidth = filterWidth/2;
        int filterHalfHeight = filterHeight/2;
        int filterArea = filterWidth * filterHeight;
        for (int y = filterHalfHeight; y < height-filterHalfHeight; y++)
        {
            for (int x = filterHalfWidth; x < width-filterHalfWidth; x++)
            {
                // Accumulate values in neighborhood
                int accumR = 0, accumG = 0, accumB = 0;
                for (int dy = -filterHalfHeight; dy <= filterHalfHeight; dy++)
                {
                    for (int dx = -filterHalfWidth; dx <= filterHalfWidth; dx++)
                    {
                        int index = (y+dy)*width + (x+dx);
                        accumR += (pixOld[index] >> 16) & 0xff;
                        accumG += (pixOld[index] >> 8) & 0xff;
                        accumB += pixOld[index] & 0xff;
                    } // dx
                } // dy

                // Normalize
                accumR /= filterArea;
                accumG /= filterArea;
                accumB /= filterArea;
                int index = y*width + x;
                pixNew[index] = 0xff000000 | (accumR << 16) | (accumG << 8) | accumB;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap = null;
        pixOld = null;
        pixNew = null;
        return bitmap;
    }

    // filterWidth and filterHeight must be odd numbers
    public Bitmap medianFilter(int filterWidth, int filterHeight,Bitmap myBitmap)
    {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pixNew = new int[width * height];
        int[] pixOld = new int[width * height];
        myBitmap.getPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap.getPixels(pixOld, 0, width, 0, 0, width, height);

        // Apply pixel-by-pixel change
        int filterHalfWidth = filterWidth/2;
        int filterHalfHeight = filterHeight/2;
        int filterArea = filterWidth * filterHeight;
        for (int y = filterHalfHeight; y < height-filterHalfHeight; y++)
        {
            for (int x = filterHalfWidth; x < width-filterHalfWidth; x++)
            {
                // Accumulate values in neighborhood
                int accumR = 0, accumG = 0, accumB = 0;
                for (int dy = -filterHalfHeight; dy <= filterHalfHeight; dy++)
                {
                    for (int dx = -filterHalfWidth; dx <= filterHalfWidth; dx++)
                    {
                        int index = (y+dy)*width + (x+dx);
                        accumR += (pixOld[index] >> 16) & 0xff;
                        accumG += (pixOld[index] >> 8) & 0xff;
                        accumB += pixOld[index] & 0xff;
                    } // dx
                } // dy

                // Normalize
                accumR /= filterArea;
                accumG /= filterArea;
                accumB /= filterArea;
                int index = y*width + x;
                pixNew[index] = 0xff000000 | (accumR << 16) | (accumG << 8) | accumB;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixNew, 0, width, 0, 0, width, height);
        myBitmap = null;
        pixOld = null;
        pixNew = null;
        return bitmap;
    }

    /*public Bitmap addPoint(Bitmap myBitmap, int a, int b) {
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] buffer = new int[width * height];
        for(int j=0;j<height;j++)
        {
            for(int i=0;i<width;i++)
            {
                int tempR,tempG,tempB;
                tempB=buffer[j*Lie*BytesPerPixel+i*BytesPerPixel+0];
                tempG=buffer[j*Lie*BytesPerPixel+i*BytesPerPixel+1];
                tempR=buffer[j*Lie*BytesPerPixel+i*BytesPerPixel+2];
//      temp=(tempR+tempG+tempB)/3;
//      temp=a*temp+b;
//      pDC->SetPixel(i,j,RGB(temp,temp,temp));
                tempB=a*tempB+b;
                tempG=a*tempG+b;
                tempR=a*tempR+b;
                pDC->SetPixel(i,j,RGB(tempR,tempG,tempB));

            }
        }
    }*/

    public Bitmap pointAdd(Bitmap myBitmap, Bitmap bitmap2) {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pix = new int[width * height];
        myBitmap.getPixels(pix, 0, width, 0, 0, width, height);

        int w = bitmap2.getWidth();
        int h = bitmap2.getHeight();
        int[] px = new int[width * height];
        bitmap2.getPixels(px, 0, w, 0, 0, w, h);

        // Apply pixel-by-pixel change
        int index = 0;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;

                int rx = (px[index] >> 16) & 0xff;
                int gx = (px[index] >> 8) & 0xff;
                int bx = px[index] & 0xff;
                //Log.e("red", "old red: " + r);
                int rn = (r + rx)/2;
                int gn = (g + gx)/2;
                int bn = (b + bx)/2;
                pix[index] = 0xff000000 | (rn << 16) | (gn << 8) | bn;
                index++;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        myBitmap = null;
        pix = null;
        return bitmap;
    }

    public Bitmap pointMinus(Bitmap myBitmap, Bitmap bitmap2) {
        // Create new array
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        int[] pix = new int[width * height];
        myBitmap.getPixels(pix, 0, width, 0, 0, width, height);

        int w = bitmap2.getWidth();
        int h = bitmap2.getHeight();
        int[] px = new int[width * height];
        bitmap2.getPixels(px, 0, w, 0, 0, w, h);
        // Apply pixel-by-pixel change
        int index = 0;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int r = (pix[index] >> 16) & 0xff;
                int g = (pix[index] >> 8) & 0xff;
                int b = pix[index] & 0xff;

                int rx = (px[index] >> 16) & 0xff;
                int gx = (px[index] >> 8) & 0xff;
                int bx = px[index] & 0xff;
                //Log.e("red", "old red: " + r);
                int rn = Math.abs(r - rx);
                int gn = Math.abs(g - gx);
                int bn = Math.abs(b - bx);
                pix[index] = 0xff000000 | (rn << 16) | (gn << 8) | bn;
                index++;
            } // x
        } // y

        // Change bitmap to use new array
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        myBitmap = null;
        pix = null;
        return bitmap;
    }
}
