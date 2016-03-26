package si.opkp;

import si.opkp.util.RequestParams;

public class RequestParamsBuilder {

	private RequestParams params;

	public RequestParamsBuilder() {
		params = new RequestParams();
	}

	public RequestParamsBuilder fields(String... fields) {
		params.setFields(String.join(",", fields));

		return this;
	}

	public RequestParamsBuilder sort(String... sort) {
		params.setSort(String.join(",", sort));

		return this;
	}

	public RequestParamsBuilder group(String... group) {
		params.setGroup(String.join(",", group));

		return this;
	}

	public RequestParamsBuilder query(String query) {
		params.setQuery(query);

		return this;
	}

	public RequestParamsBuilder skip(int skip) {
		params.setSkip(skip);

		return this;
	}

	public RequestParamsBuilder take(int take) {
		params.setTake(take);

		return this;
	}

	public RequestParams build() {
		return params;
	}

}
