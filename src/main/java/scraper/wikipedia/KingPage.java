package scraper.wikipedia;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import database.Database;
import entity.Character;
import entity.type.YearRange;

public class KingPage extends Base {
	/**
	 * Contains infomation parsed from the parent page.
	 */
	private Character preParsed;

	public KingPage(String path, Character preParsed) {
		super(path);
		this.preParsed = preParsed;
	}

	private static Integer parseYear(String input) {
		// Pre-defining some regex
		Pattern dateRegex = Pattern.compile("(\\d+ )?[Tt]háng \\d+,? (năm )?(\\d+)");
		Pattern numberRegex = Pattern.compile("\\d+");

		Matcher mBorn = dateRegex.matcher(input);
		if (mBorn.find()) return Integer.parseInt(mBorn.group(3));
		mBorn = numberRegex.matcher(input);
		if (mBorn.find()) return Integer.parseInt(mBorn.group());
		return null;
	}

	@Override
	public Database getResult() {
		var database = new Database();
		database.getCharacters().add(preParsed);

		// Check for the detailed "infobox", as we're too underdeveloped to parse
		// a block of raw text.
		Element infoBox = document.selectFirst("table.infobox");
		if (infoBox == null) return database;

		// Infomation to be parsed into
		String father = null;
		Set<String> children = new HashSet<>();
		Integer born = null;
		Integer died = null;

		for (Element row: infoBox.select("tbody > tr")) {
			// Look for rows with exactly 2 cells.
			Element keyCell = row.selectFirst("th");
			Element valueCell = row.selectFirst("td");
			if (keyCell == null || valueCell == null) continue;
			var key = keyCell.text();
			var value = valueCell.text();

			// Check for unknown value
			if (value.equals("không có") || value.equals("không rõ")) continue;

			// Parse more info
			switch(key) {
			case "Sinh":
				born = parseYear(value);
				break;
			case "Mất":
				died = parseYear(value);
				break;
			case "Hậu duệ":
				children = valueCell
					.select("a")
					.stream()
					.map(e -> e.ownText())
					.filter(text -> !text.equals(""))
					.collect(Collectors.toSet());
				break;
			case "Thân phụ":
				father = value;
				break;
			case "Tên thật":
			case "Tên húy":
			case "Thân mẫu": // Mother
			case "Vợ":
			case "An táng":
			case "Triều đại": // We will look for reign based on the crowned years.
			case "Trị vì":
			case "Tại vị":
			case "Tại nhiệm":
			case "Đăng quang":
			case "Tiền nhiệm": // Previous in power
			case "Kế nhiệm": // Next in power
			case "Chữ ký":
			case "Hoàng gia ca":
			case "Thê thiếp":
			case "Tôn giáo":
			case "Hoàng tộc":
			case "Gia tộc":
			case "Nghề nghiệp":
			case "Thời kỳ":
			case "Phối ngẫu":
			case "Tước hiệu":
			case "Tên đầy đủ":
			case "Nhiếp chính":
			case "Chấp chính":
			case "Thái thượng hoàng":
			case "Ở ngôi":
			case "Húy":
			case "Thụy hiệu":
			case "trị vì":
			case "Vương tộc":
			case "Thời vua":
			case "Tước vị":
			case "Chúa Trịnh":
			case "Nhiếp chinh":
			case "Hậu phi":
			case "Niên hiệu":
			case "Phụ chính":
			case "Đồng trị vì":
			case "Lãnh đạo":
			case "Tên":
			case "Chính thất":
			case "Các vợ khác":
			case "Thượng hoàng":
			case "Thái Thượng hoàng":
			case "Hoàng hậu":
			case "Vương hậu":
			default:
				break;
			}
		}

		database.getCharacters().clear();
		var character = new Character(
			preParsed.getName(),
			father,
			new YearRange(born, died),
			preParsed.getCrowned(),
			getUrl()
		);
		character.getAliases().addAll(preParsed.getAliases());
		character.getChildren().addAll(children);
		database.getCharacters().add(character);
		return database;
	}
}
