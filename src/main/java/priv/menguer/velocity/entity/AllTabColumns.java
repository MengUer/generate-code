package priv.menguer.velocity.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 11:17:09
 * @verifier
 * @check
 * @update
 * @remark
 */
public class AllTabColumns implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3005255592923967603L;

	private String owner;

	/**
	 * Table, view or cluster name
	 */
	private String tableName;

	/**
	 * Column name
	 */
	private String columnName;

	/**
	 * Datatype of the column
	 */
	private String dataType;

	/**
	 * Datatype modifier of the column
	 */
	private String dataTypeMod;

	private String dataTypeOwner;

	/**
	 * Length of the column in bytes
	 */
	private Integer dataLength;

	/**
	 * Length: decimal digits (NUMBER) or binary digits (FLOAT)
	 */
	private Integer dataPrecision;
	private Integer dataScale;
	private String nullable;
	private Integer columnId;
	private Integer defaultLength;
	private Long dataDefault;
	private Integer numDistinct;
	private String lowValue;
	private String highValue;
	private Integer density;
	private Integer numNulls;
	private Integer numBuckets;
	private LocalDateTime lastAnalyzed;
	private Integer sampleSize;
	private String characterSetName;
	private Integer charColDeclLength;
	private String globalStats;
	private String userStats;
	private Integer avgColLen;
	private Integer charLength;
	private String charUsed;
	private String v80FmtImage;
	private String dataUpgraded;
	private String histogram;
	private String defaultOnNull;
	private String identityColumn;
	private String evaluationEdition;
	private String unusableBefore;
	private String unusableBeginning;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"owner\":\"").append(owner).append("\", \"tableName\":\"").append(tableName)
				.append("\", \"columnName\":\"").append(columnName).append("\", \"dataType\":\"").append(dataType)
				.append("\", \"dataTypeMod\":\"").append(dataTypeMod).append("\", \"dataTypeOwner\":\"")
				.append(dataTypeOwner).append("\", \"dataLength\":\"").append(dataLength)
				.append("\", \"dataPrecision\":\"").append(dataPrecision).append("\", \"dataScale\":\"")
				.append(dataScale).append("\", \"nullable\":\"").append(nullable).append("\", \"columnId\":\"")
				.append(columnId).append("\", \"defaultLength\":\"").append(defaultLength)
				.append("\", \"dataDefault\":\"").append(dataDefault).append("\", \"numDistinct\":\"")
				.append(numDistinct).append("\", \"lowValue\":\"").append(lowValue).append("\", \"highValue\":\"")
				.append(highValue).append("\", \"density\":\"").append(density).append("\", \"numNulls\":\"")
				.append(numNulls).append("\", \"numBuckets\":\"").append(numBuckets).append("\", \"lastAnalyzed\":\"")
				.append(lastAnalyzed).append("\", \"sampleSize\":\"").append(sampleSize)
				.append("\", \"characterSetName\":\"").append(characterSetName).append("\", \"charColDeclLength\":\"")
				.append(charColDeclLength).append("\", \"globalStats\":\"").append(globalStats)
				.append("\", \"userStats\":\"").append(userStats).append("\", \"avgColLen\":\"").append(avgColLen)
				.append("\", \"charLength\":\"").append(charLength).append("\", \"charUsed\":\"").append(charUsed)
				.append("\", \"v80FmtImage\":\"").append(v80FmtImage).append("\", \"dataUpgraded\":\"")
				.append(dataUpgraded).append("\", \"histogram\":\"").append(histogram)
				.append("\", \"defaultOnNull\":\"").append(defaultOnNull).append("\", \"identityColumn\":\"")
				.append(identityColumn).append("\", \"evaluationEdition\":\"").append(evaluationEdition)
				.append("\", \"unusableBefore\":\"").append(unusableBefore).append("\", \"unusableBeginning\":\"")
				.append(unusableBeginning).append("\"}");
		return builder.toString();
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeMod() {
		return dataTypeMod;
	}

	public void setDataTypeMod(String dataTypeMod) {
		this.dataTypeMod = dataTypeMod;
	}

	public String getDataTypeOwner() {
		return dataTypeOwner;
	}

	public void setDataTypeOwner(String dataTypeOwner) {
		this.dataTypeOwner = dataTypeOwner;
	}

	public Integer getDataLength() {
		return dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public Integer getDataPrecision() {
		return dataPrecision;
	}

	public void setDataPrecision(Integer dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public Integer getDataScale() {
		return dataScale;
	}

	public void setDataScale(Integer dataScale) {
		this.dataScale = dataScale;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getDefaultLength() {
		return defaultLength;
	}

	public void setDefaultLength(Integer defaultLength) {
		this.defaultLength = defaultLength;
	}

	public Long getDataDefault() {
		return dataDefault;
	}

	public void setDataDefault(Long dataDefault) {
		this.dataDefault = dataDefault;
	}

	public Integer getNumDistinct() {
		return numDistinct;
	}

	public void setNumDistinct(Integer numDistinct) {
		this.numDistinct = numDistinct;
	}

	public String getLowValue() {
		return lowValue;
	}

	public void setLowValue(String lowValue) {
		this.lowValue = lowValue;
	}

	public String getHighValue() {
		return highValue;
	}

	public void setHighValue(String highValue) {
		this.highValue = highValue;
	}

	public Integer getDensity() {
		return density;
	}

	public void setDensity(Integer density) {
		this.density = density;
	}

	public Integer getNumNulls() {
		return numNulls;
	}

	public void setNumNulls(Integer numNulls) {
		this.numNulls = numNulls;
	}

	public Integer getNumBuckets() {
		return numBuckets;
	}

	public void setNumBuckets(Integer numBuckets) {
		this.numBuckets = numBuckets;
	}

	public LocalDateTime getLastAnalyzed() {
		return lastAnalyzed;
	}

	public void setLastAnalyzed(LocalDateTime lastAnalyzed) {
		this.lastAnalyzed = lastAnalyzed;
	}

	public Integer getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Integer sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

	public Integer getCharColDeclLength() {
		return charColDeclLength;
	}

	public void setCharColDeclLength(Integer charColDeclLength) {
		this.charColDeclLength = charColDeclLength;
	}

	public String getGlobalStats() {
		return globalStats;
	}

	public void setGlobalStats(String globalStats) {
		this.globalStats = globalStats;
	}

	public String getUserStats() {
		return userStats;
	}

	public void setUserStats(String userStats) {
		this.userStats = userStats;
	}

	public Integer getAvgColLen() {
		return avgColLen;
	}

	public void setAvgColLen(Integer avgColLen) {
		this.avgColLen = avgColLen;
	}

	public Integer getCharLength() {
		return charLength;
	}

	public void setCharLength(Integer charLength) {
		this.charLength = charLength;
	}

	public String getCharUsed() {
		return charUsed;
	}

	public void setCharUsed(String charUsed) {
		this.charUsed = charUsed;
	}

	public String getV80FmtImage() {
		return v80FmtImage;
	}

	public void setV80FmtImage(String v80FmtImage) {
		this.v80FmtImage = v80FmtImage;
	}

	public String getDataUpgraded() {
		return dataUpgraded;
	}

	public void setDataUpgraded(String dataUpgraded) {
		this.dataUpgraded = dataUpgraded;
	}

	public String getHistogram() {
		return histogram;
	}

	public void setHistogram(String histogram) {
		this.histogram = histogram;
	}

	public String getDefaultOnNull() {
		return defaultOnNull;
	}

	public void setDefaultOnNull(String defaultOnNull) {
		this.defaultOnNull = defaultOnNull;
	}

	public String getIdentityColumn() {
		return identityColumn;
	}

	public void setIdentityColumn(String identityColumn) {
		this.identityColumn = identityColumn;
	}

	public String getEvaluationEdition() {
		return evaluationEdition;
	}

	public void setEvaluationEdition(String evaluationEdition) {
		this.evaluationEdition = evaluationEdition;
	}

	public String getUnusableBefore() {
		return unusableBefore;
	}

	public void setUnusableBefore(String unusableBefore) {
		this.unusableBefore = unusableBefore;
	}

	public String getUnusableBeginning() {
		return unusableBeginning;
	}

	public void setUnusableBeginning(String unusableBeginning) {
		this.unusableBeginning = unusableBeginning;
	}
}
