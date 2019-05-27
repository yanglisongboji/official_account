package test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test4 {

	public static void main(String[] args) throws IOException {

//		try (CloseSon a = new CloseSon(); CloseSon b = new CloseSon()) {
//			a.doSomething(10);
//			a.doSomething(0);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

		List<BindingRegionTemplatesDto> list = new ArrayList<>();
		String[] stockIdStoreId = new String[] { "97792_2223" };

		List<String> spList = Arrays.asList("97792_2479");
		Arrays.stream(stockIdStoreId).forEach(p -> {
			try {
				String[] split = p.split("_");
				Long stockId = Long.valueOf(split[0]);
				String oldChar = stockId + "_";
				List<String> collect = spList.stream().filter(sp -> sp.contains(oldChar)).collect(Collectors.toList());
				if (collect.size() > 0) {
					String sp = collect.get(0);

					Long storeId = Long.valueOf(split[1]);
					Long productId = Long.valueOf(sp.split("_")[1]);
					if (null != storeId && null != productId && storeId > 0 && productId > 0) {
						BindingRegionTemplatesDto b = new BindingRegionTemplatesDto();
						b.setStoreId(storeId);
						b.setProductId(productId);
						list.add(b);
					}
				}
			} catch (Exception e) {
			}
		});

		System.out.println(list);
	}

	static class BindingRegionTemplatesDto {
		private Long storeId;
		private Long productId;

		public Long getStoreId() {
			return storeId;
		}

		public void setStoreId(Long storeId) {
			this.storeId = storeId;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		@Override
		public String toString() {
			return "BindingRegionTemplatesDto [storeId=" + storeId + ", productId=" + productId + "]";
		}

	}
}
