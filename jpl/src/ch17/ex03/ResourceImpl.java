package ch17.ex03;

import ch17.ex03.ResourceManager.Resource;

// TODO 17.3
// ハッシュコードを使用する代わりに、キーを管理することで参照オブジェクトを使用するように、
// リソース実装クラスを書き直しなさい。

public class ResourceImpl implements Resource{
	int keyHash;
	boolean needsRelease = false;

	public ResourceImpl(Object key) {
		keyHash = System.identityHashCode(key);

		// ..外部リソースの設定

		needsRelease = true;
	}

	public void use(Object key, Object... args) {
		if (System.identityHashCode(key) != keyHash)
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
