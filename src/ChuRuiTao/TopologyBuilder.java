package ChuRuiTao;

public class TopologyBuilder {
	private Polygon polygon;
	Line[] LineArray;

	public TopologyBuilder(Polygon polygon) {
		this.polygon = polygon;
		topology();
	}

	public void topology() {
		// TODO Auto-generated method stub

		polygon.lineInDotInOrder(polygon);

		// ///////////////////////////////////////////////////////////////
		LineArray = polygon.Lines.toArray(new Line[0]);

		for (int s = 0; s < LineArray.length; s++) {
			Line Sc = LineArray[s]; // the current arc
			if (Sc.leftPoly != null && Sc.rightPoly != null)
				continue;

			if (Sc.leftPoly == null) {
				Polygon pi = new Polygon();
				Dot N0 = new Dot();
				Dot Nc = new Dot();
				pi.Lines.add(Sc);
				Sc.leftPoly = pi;
				N0 = Sc.getStartDot();
				Nc = Sc.getEndDot();
				// ///////////////////Step One///////////////////////////
				while (!N0.equals(Nc)) {
					for (int i = 0; i < LineArray.length; i++) { // reserve
																	// the
																	// change
						if (LineArray[i].index == Sc.index) {
							LineArray[i] = Sc;
							break;
						}
					}
					Sc = Nc.findNextLine(Sc);
					if (!pi.Lines.contains(Sc))
						pi.Lines.add(Sc);
					if (Nc.equals(Sc.getStartDot())) {
						Sc.leftPoly = pi;
						Nc = Sc.getEndDot();

					} else if (Nc.equals(Sc.getEndDot())) {
						Sc.rightPoly = pi;
						Nc = Sc.getStartDot();
					}

				}
				for (int i = 0; i < LineArray.length; i++) {// reserve the
															// change
					if (LineArray[i].index == Sc.index) {
						LineArray[i] = Sc;
						break;
					}
				}
			} else {
				if (Sc.rightPoly == null) { // ÅÐ¿ÕÎÊÌâ
					Polygon pi = new Polygon();
					Dot N0 = new Dot();
					Dot Nc = new Dot();

					pi.Lines.add(Sc);
					Sc.rightPoly = pi;
					N0 = Sc.getEndDot();
					Nc = Sc.getStartDot();

					// ///////////////////Step
					// Two///////////////////////////
					while (!N0.equals(Nc)) {
						for (int i = 0; i < LineArray.length; i++) {// reserve
																	// the
																	// change
							if (LineArray[i].index == Sc.index) {
								LineArray[i] = Sc;
								break;
							}
						}
						Sc = Nc.findNextLine(Sc);
						if (!pi.Lines.contains(Sc))
							pi.Lines.add(Sc);
						if (Nc.equals(Sc.getStartDot())) {
							Sc.leftPoly = pi;
							Nc = Sc.getEndDot();
						} else if (Nc.equals(Sc.getEndDot())) {
							Sc.rightPoly = pi;
							Nc = Sc.getStartDot();
						}
					}
					for (int i = 0; i < LineArray.length; i++) {// reserve
																// the
																// change
						if (LineArray[i].index == Sc.index) {
							LineArray[i] = Sc;
							break;
						}
					}
				}
			}
		}

	}
	

}
