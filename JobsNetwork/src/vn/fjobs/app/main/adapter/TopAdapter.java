package vn.fjobs.app.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.fjobs.R;
import vn.fjobs.app.main.entity.TopEntities;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder> {

    private List<TopEntities> topEntitiesList;

    public TopAdapter(List<TopEntities> topEntitiesList){
        this.topEntitiesList = topEntitiesList;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);

        return new TopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        TopEntities topEntities = topEntitiesList.get(position);
        holder.nameCompany.setText(topEntities.getNameCompany());
        holder.desCompany.setText(topEntities.getCompanyDes());
    }

    @Override
    public int getItemCount() {
        return topEntitiesList.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder{

        public ImageView bannerCompany, logoCompany;
        public TextView nameCompany, desCompany;

        public TopViewHolder(View view){
            super(view);

            bannerCompany = (ImageView)view.findViewById(R.id.item_top_banner_company);
            logoCompany = (ImageView) view.findViewById(R.id.item_top_logo_company);
            nameCompany = (TextView) view.findViewById(R.id.item_top_name_company);
            desCompany = (TextView) view.findViewById(R.id.item_top_company_des);
        }
    }

    public void clearAllData() {
        topEntitiesList.clear();
        notifyDataSetChanged();
    }
}
