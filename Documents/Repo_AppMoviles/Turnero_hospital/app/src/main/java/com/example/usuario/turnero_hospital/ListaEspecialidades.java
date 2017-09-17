package com.example.usuario.turnero_hospital;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaEspecialidades  extends ListActivity implements
        AdapterView.OnItemClickListener {

    private DialogInfoAdapter adapter;
    private DialogInfo dialogInfoToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_especialidades);

        adapter = new DialogInfoAdapter();

        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);
        loadDialogData();
    }
    private void loadDialogData() {


        DialogInfo info = new DialogInfo();
        info.setTitle("Clinica");

        adapter.addDialogInfo(info);

        info = new DialogInfo();
        info.setTitle("Cardialogo");
        adapter.addDialogInfo(info);

        info = new DialogInfo();
        info.setTitle("Dermatologo");
        adapter.addDialogInfo(info);

        info = new DialogInfo();
        info.setTitle("Neurologo");
        adapter.addDialogInfo(info);

        info = new DialogInfo();
        info.setTitle("Oncologo");
        adapter.addDialogInfo(info);


        info = new DialogInfo();
        info.setTitle("Pediatra");
        adapter.addDialogInfo(info);

        adapter.notifyDataSetChanged();

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Calendario.class);
        startActivity(intent);
    }

    class DialogInfoAdapter extends BaseAdapter {
        private ArrayList<DialogInfo> dialogInfos;
        private LayoutInflater inflater;

        public DialogInfoAdapter() {
            dialogInfos = new ArrayList<DialogInfo>();
            inflater = LayoutInflater.from(ListaEspecialidades.this);
        }

        public void addDialogInfo(DialogInfo info) {
            if (info != null) {
                dialogInfos.add(info);
            }
        }

        @Override
        public int getCount() {
            return dialogInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return dialogInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class Holder {
            private TextView txtLabel;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_dialog_info, null);
                holder = new Holder();
                holder.txtLabel = (TextView) convertView
                        .findViewById(R.id.txt_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();

            }

            DialogInfo info = (DialogInfo) getItem(position);
            holder.txtLabel.setText(info.getTitle());

            return convertView;
        }

    }
}
