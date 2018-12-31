package org.open918.sample_app_android.activity.table;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.open918.lib.domain.TicketField;
import org.open918.sample_app_android.R;

import java.util.List;


/**
 * Created by Joel Haasnoot on 19/10/15.
 */
public class TicketFieldsAdapter extends RecyclerView.Adapter<TicketFieldsAdapter.ViewHolder> {
    private final List<TicketField> fields;
    private final Context context;

    public TicketFieldsAdapter(Context ctx, List<TicketField> ticketFields) {
        this.context = ctx;
        this.fields = ticketFields;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.row_ticket_field, parent, false);

        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketField field = fields.get(position);
        holder.textLine.setText(String.valueOf(field.getLine()));
        holder.textColumn.setText(String.valueOf(field.getColumn()));
        holder.textWidth.setText(String.valueOf(field.getWidth()));
        String txt = field.getText();
        if (txt.length() == 0) {
            holder.textContents.setText(R.string.label_blank);
            holder.textContents.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.textContents.setText(txt);
            holder.textContents.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView textLine;
        TextView textColumn;
        TextView textWidth;
        TextView textContents;

        public ViewHolder(View itemView) {
            super(itemView);
            textLine = itemView.findViewById(R.id.text_line);
            textColumn = itemView.findViewById(R.id.text_column);
            textWidth = itemView.findViewById(R.id.text_width);
            textContents = itemView.findViewById(R.id.text_contents);
            item = itemView;
        }
    }
}
