function setDefaultBirthDate(input) {
    // すでに値が入っているなら何もしない（保持）
    if (input.value) return;

    // 今日から20年前を計算
    const today = new Date();
    const twentyYearsAgo = new Date(
        today.getFullYear() - 20,
        today.getMonth(),
        today.getDate()
    );

    // yyyy-MM-dd に整形
    const yyyy = twentyYearsAgo.getFullYear();
    const mm = String(twentyYearsAgo.getMonth() + 1).padStart(2, '0');
    const dd = String(twentyYearsAgo.getDate()).padStart(2, '0');

    input.value = `${yyyy}-${mm}-${dd}`;
}