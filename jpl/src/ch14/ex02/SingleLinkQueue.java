package ch14.ex02;

// P215~P222



public class SingleLinkQueue<E> {
	class Cell {
		private Cell next;
		private E element;
		public Cell(E element) {
			this.element = element;
		}
		public Cell(E element, Cell next) {
			this.element = element;
			this.next = next;
		}
		public E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}
		public Cell getNext() {
			return next;
		}
		public void setNext(Cell next) {
			this.next = next;
		}
	}

	protected Cell head;
	protected Cell tail;

	public void add(E item) {
		Cell cell = new Cell(item);
		if (tail == null) {
			head = tail = cell;
		} else {
			tail.setNext(cell);
			tail = cell;
		}
	}
	public E remove() {
		if (head == null)
			return null;
		Cell cell = head;
		head = head.getNext();
		if (head == null)
			tail = null;
		return cell.getElement();
	}
	public int size() {
		Cell cur = head;
		if (cur == null) return 0;
		int count = 1;
		while((cur = cur.getNext()) != null)
			count++;
		return count;
	}
}