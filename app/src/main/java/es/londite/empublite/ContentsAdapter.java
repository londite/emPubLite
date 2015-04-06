package es.londite.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.support.v13.app.FragmentStatePagerAdapter;

public class ContentsAdapter extends FragmentStatePagerAdapter{
    private BookContents contents = null;

    public ContentsAdapter(Activity ctx, BookContents contents) {
        super(ctx.getFragmentManager());

        this.contents= this.contents;
    }

    @Override
    public Fragment getItem(int position){
        String path=contents.getChapterFile(position);

        return(SimpleContentFragment.newInstance("file:///android_asset/book/"+path));
    }

    @Override
    public int getCount(){
        return (contents.getChapterCount());
    }
}