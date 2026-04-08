package com.example.toshio.demo.enums;

public enum ApplicationSteps {
    PRODUCT(1, "/contract/product","1. 商品選択"),
    PLAN(2, "/contract/plan","2. プラン選択"),
    ELIGIBILITY(3, "/contract/eligibility","3. 申込可否判定"),
    POLICYHOLDER(4, "/contract/policyholder","4. 契約者情報"),
    INSURED(5, "/contract/insured","5. 被保険者情報"),
    CONFIRM(6, "/contract/confirm","6. 内容確認"),
    COMPLETE(7, "/contract/complete","7. 完了");

    private final int order;
    private final String path;
    private final String displayName;
    ApplicationSteps(int order, String path, String displayName) {
        this.order = order;
        this.path = path;
        this.displayName = displayName;
    }
    public int getOrder() {
        return order;
    }
    public String getPath() {
        return path;
    }
    public String getDisplayName() {
        return displayName;
    }
}
