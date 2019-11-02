package action;

import java.util.ArrayList;
import java.util.List;
//都道府県名をListに格納し、それを返すクラス
public class Prefecture {
	private final String HOKKAIDO = "北海道";
	private final String AOMORI = "青森県";
	private final String IWATE = "岩手県";
	private final String MIYAGI = "宮城県";
	private final String AKITA = "秋田県";
	private final String YAMAGATA = "山形県";
	private final String FUKUSHIMA = "福島県";
	private final String IBARAKI = "茨城県";
	private final String TOCHIGI = "栃木県";
	private final String GUNMA = "群馬県";
	private final String SAITAMA = "埼玉県";
	private final String CHIBA = "千葉県";
	private final String TOKYO = "東京都";
	private final String KANAGAWA = "神奈川県";
	private final String NIIGATA = "新潟県";
	private final String TOYAMA = "富山県";
	private final String ISHIKAWA = "石川県";
	private final String FUKUI = "福井県";
	private final String YAMANASHI = "山梨県";
	private final String NAGANO = "長野県";
	private final String GIFU = "岐阜県";
	private final String SHIZUOKA = "静岡県";
	private final String MIE = "三重県";
	private final String SHIGA = "滋賀県";
	private final String KYOTO = "京都府";
	private final String OSAKA = "大阪府";
	private final String HYOGO = "兵庫県";
	private final String NARA = "奈良県";
	private final String WAKAYAMA = "和歌山県";
	private final String TOTTORI = "鳥取県";
	private final String SHIMANE = "島根県";
	private final String OKAYAMA = "岡山県";
	private final String HIROSHIMA = "広島県";
	private final String YAMAGUCHI = "山口県";
	private final String TOKUSHIMA = "徳島県";
	private final String KAGAWA = "香川県";
	private final String EHIME = "愛媛県";
	private final String KOCHI = "高知県";
	private final String FUKUOKA = "福岡県";
	private final String SAGA = "佐賀県";
	private final String NAGASAKI = "長崎県";
	private final String KUMAMOTO = "熊本県";
	private final String OITA = "大分県";
	private final String MIYAZAKI = "宮崎県";
	private final String KAGOSHIMA = "鹿児島県";
	private final String OKINAWA = "沖縄県";

	public Prefecture() {
	}

	public List<String> pList() {
		List<String> pList = new ArrayList<>();;
		pList.add(HOKKAIDO);
		pList.add(AOMORI);
		pList.add(IWATE);
		pList.add(MIYAGI);
		pList.add(AKITA);
		pList.add(YAMAGATA);
		pList.add(FUKUSHIMA);
		pList.add(IBARAKI);
		pList.add(TOCHIGI);
		pList.add(GUNMA);
		pList.add(SAITAMA);
		pList.add(CHIBA);
		pList.add(TOKYO);
		pList.add(KANAGAWA);
		pList.add(NIIGATA);
		pList.add(TOYAMA);
		pList.add(ISHIKAWA);
		pList.add(FUKUI);
		pList.add(YAMANASHI);
		pList.add(NAGANO);
		pList.add(GIFU);
		pList.add(SHIZUOKA);
		pList.add(MIE);
		pList.add(SHIGA);
		pList.add(KYOTO);
		pList.add(OSAKA );
		pList.add(HYOGO );
		pList.add(NARA);
		pList.add(WAKAYAMA);
		pList.add(TOTTORI);
		pList.add(SHIMANE);
		pList.add(OKAYAMA);
		pList.add(HIROSHIMA);
		pList.add(YAMAGUCHI);
		pList.add(TOKUSHIMA);
		pList.add(KAGAWA);
		pList.add(EHIME);
		pList.add(KOCHI);
		pList.add(FUKUOKA);
		pList.add(SAGA);
		pList.add(NAGASAKI);
		pList.add(KUMAMOTO);
		pList.add(OITA);
		pList.add(MIYAZAKI);
		pList.add(KAGOSHIMA);
		pList.add(OKINAWA);
		return pList;
	}
}
