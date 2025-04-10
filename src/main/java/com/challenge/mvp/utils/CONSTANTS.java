package com.challenge.mvp.utils;

public class CONSTANTS {

	public enum AccessLevel {
		ADMIN(1), USER(2);

		public final Integer level;

		private AccessLevel(Integer level) {
			this.level = level;
		}

		public static AccessLevel fromLevel(int level) {
			for (AccessLevel accessLevel : values()) {
				if (accessLevel.level.equals(level)) {
					return accessLevel;
				}
			}
			throw new IllegalArgumentException("Invalid access level: " + level);
		}
	}

	public enum UserState {
		ACTIVE(1), DEACTIVATE(0);

		public final Integer state;

		private UserState(Integer state) {
			this.state = state;
		}
	}

	public enum ProductState {
		ACTIVE(1), DEACTIVATE(0);

		public final Integer state;

		private ProductState(Integer state) {
			this.state = state;
		}
	}

	public enum ProductCategoryState {
		ACTIVE(1), DEACTIVATE(0);

		public final Integer state;

		private ProductCategoryState(Integer state) {
			this.state = state;
		}
	}

	public enum ProductTagState {
		ACTIVE(1), DEACTIVATE(0);

		public final Integer state;

		private ProductTagState(Integer state) {
			this.state = state;
		}
	}

}