package toplana.specifications;

public class SearchCriteria {
	private String entityName;
	private String key;
    private Object value;
    private SearchOperation operation;

    public SearchCriteria() {
    }

    public SearchCriteria(String entityName, String key, Object value, SearchOperation operation) {
        this.entityName = entityName;
    	this.key = key;
        this.value = value;
        this.operation = operation;
    }

    // getters and setters, equals(), toString(), ... (omitted for brevity)

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }
    
    

    public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "SearchCriteria [entityName=" + entityName + ", key=" + key + ", value=" + value + ", operation="
				+ operation + "]";
	}

	
}