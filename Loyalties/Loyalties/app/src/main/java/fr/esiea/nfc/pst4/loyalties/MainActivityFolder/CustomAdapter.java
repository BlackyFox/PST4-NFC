package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Adapter pour le ListView.                                                                      */
/**************************************************************************************************/

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.esiea.nfc.pst4.loyalties.R;


public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.logo);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.name);

        RowItem row_pos = rowItem.get(position);
        String path = context.getFilesDir().getAbsolutePath();
        Bitmap logo = BitmapFactory.decodeFile(path + "/" + row_pos.getLogo());
        Bitmap newLogo = resizeImage(logo, 50, 100);

        imgIcon.setImageBitmap(newLogo);
        txtTitle.setText(row_pos.getName());

        return convertView;
    }

    public static Bitmap resizeImage(Bitmap image, int maxWidth, int maxHeight) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double imageAspect = (double) imageWidth / imageHeight;
        double canvasAspect = (double) maxWidth / maxHeight;
        double scaleFactor;

        if (imageAspect < canvasAspect) {
            scaleFactor = (double) maxHeight / imageHeight;
        } else {
            scaleFactor = (double) maxWidth / imageWidth;
        }

        float scaleWidth = ((float) scaleFactor) * imageWidth;
        float scaleHeight = ((float) scaleFactor) * imageHeight;

        // recreate the new Bitmap
        return Bitmap.createScaledBitmap(image, (int) scaleWidth, (int) scaleHeight, true);
    }
}
