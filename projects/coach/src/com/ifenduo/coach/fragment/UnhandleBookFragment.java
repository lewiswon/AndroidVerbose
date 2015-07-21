package com.ifenduo.coach.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.API;
import com.ifenduo.coach.App;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.MainActivity;
import com.ifenduo.coach.activity.MainActivity.OnResultListener;
import com.ifenduo.coach.adapter.BookListAdapter;
import com.ifenduo.coach.bean.Book;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;

public class UnhandleBookFragment extends BaseFragment implements OnRefreshListener2<ListView>, OnResultListener {
	public static final int TAG_UNHANDLE = 1, FROM_BOOK_LIST = 1003, REQUEST_CODE = 10001;

	private PullToRefreshListView bookListView;
	private BookListAdapter adapter;
	private ArrayList<Book> list;
	private int page = 1, total;
	private boolean isCreated = false;
	@Override
	public int setLayout() {
		return R.layout.fragment_unhandled_book;
	}
	@Override
	public void initViews() {

		list = new ArrayList<Book>();
		bookListView = (PullToRefreshListView) findViewById(R.id.lv_list_unhandle_book);
		adapter = new BookListAdapter(getActivity(), list, R.layout.item_book_list, TAG_UNHANDLE);
		bookListView.setAdapter(adapter);
		// bookListView.setEmptyView(findViewById(R.id.rl_loading));
		bookListView.setOnRefreshListener(this);
		bookListView.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		if (!isCreated) {
			getData();
		}
		isCreated = true;
		((MainActivity) getActivity()).setOnResultListener(this);
	}
	public void getData() {
		Type type = new TypeToken<BaseResponse<Book>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put(API.Book.key_id, app.getCurrentUser().getId());
		params.put(API.Book.key_type, API.Book.unhandle);
		params.put("pageNow", page + "");
		new Request<Book>(getActivity()).getList(API.Book.url, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<Book>() {

			@Override
			public void onSuccess(ArrayList<Book> bookList, int total) {
				if (bookListView != null) {
					bookListView.onRefreshComplete();
				}
				if (bookList.size() > 0) {
					UnhandleBookFragment.this.total = total;
					if (page == 1) {
						list = new ArrayList<Book>();
						list.addAll(bookList);
					} else {
						list.addAll(bookList);
					}
					adapter.setList(list);
				} else {
					list.clear();
					adapter.setList(list);
					bookListView.setEmptyView(findViewById(R.id.tv_empty));
				}
			}

			@Override
			public void onError(String error) {
				bookListView.onRefreshComplete();
			}
		});
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		getData();
	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (Constant.PAGE_SIZE * page < total) {
			page += 1;
			getData();
		} else {
			Message msg = new Message();
			msg.what = app.REFRESH;
			msg.obj = bookListView;
			app.handler.sendMessage(msg);
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isCreated) {
			if (app == null) {
				app = (App) getActivity().getApplication();
			}
			getData();
		}
	}
	@Override
	public void onresult() {
		getData();
	}
}
