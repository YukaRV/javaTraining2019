package ch17.ex03;

import java.util.WeakHashMap;

import ch17.ex03.ResourceManager.Resource;

// TODO 17.3
// ハッシュコードを使用する代わりに、キーを管理することで参照オブジェクトを使用するように、
// リソース実装クラスを書き直しなさい。

public class ResourceImpl implements Resource{
	boolean needsRelease = false;
	WeakHashMap<Object, Object> keys;

	public ResourceImpl(Object key) {

		// ..外部リソースの設定
		// 外部リソースの代わり
		Object resourceObj = new Object();
		keys.put(key, resourceObj);
		needsRelease = true;
	}

	public void use(Object key, Object... args) {
		if (!keys.containsKey(key))
			throw new IllegalArgumentException("wrong key");

		// ... リソースの使用 ...
	}

	public synchronized void release() {
		if (needsRelease) {
			needsRelease = false;

			// ... リソースの解放 ...
		}
	}
}
