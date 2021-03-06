
package com.jfixby.util.patch18.red.fields.util;

import com.jfixby.r3.ext.api.patch18.Patch18;
import com.jfixby.util.patch18.red.fields.FieldsFactory;
import com.jfixby.util.patch18.red.fields.afield.Patch18Cell;
import com.jfixby.util.patch18.red.fields.afield.WallFunction;
import com.jfixby.util.patch18.red.fields.afield.WallsFieldSchematic;
import com.jfixby.util.patch18.red.fields.bool.BOOL;
import com.jfixby.util.patch18.red.fields.bool.BoolFunction;

public class WallFunctionCore {

	private final WallFunction a_patch_field;
	private final BoolFunction bool_field;

	public WallFunctionCore (final WallFunction a_patch_field, final BoolFunction bool_field) {
		this.a_patch_field = a_patch_field;
		this.bool_field = bool_field;

	}

	public Patch18Cell getValue (final long grid_cell_x, final long grid_cell_y) {

		final Patch18 patch_value = boolToAPatch(this.bool_field, grid_cell_x, grid_cell_y);
		return this.a_patch_field.newValue(patch_value);
	}

	public static WallFunction process (final BoolFunction bool_field) {

		final WallsFieldSchematic field_properties = FieldsFactory.newWallsFieldProperties();

		final WallFunction a_patch_field = FieldsFactory.newWallsField(field_properties);

		final WallFunctionCore core = new WallFunctionCore(a_patch_field, bool_field);
		a_patch_field.setCore(core);

		return a_patch_field;
	}

	private static Patch18 boolToAPatch (final BoolFunction bool_field, final long grid_cell_x, final long grid_cell_y) {
		final BOOL ul = bool_field.getValue(grid_cell_x, grid_cell_y);
		final BOOL dl = bool_field.getValue(grid_cell_x, grid_cell_y + 1);
		final BOOL ur = bool_field.getValue(grid_cell_x + 1, grid_cell_y);
		final BOOL br = bool_field.getValue(grid_cell_x + 1, grid_cell_y + 1);

		if (ul.isUnknown() || dl.isUnknown() || ur.isUnknown() || br.isUnknown()) {
			return Patch18.Irrelevant;

		}

		final boolean TL = ul.isTrue();
		final boolean BL = dl.isTrue();
		final boolean TR = ur.isTrue();
		final boolean BR = br.isTrue();

		if (TL & BL & TR & BR) {// 1111
			return Patch18.Blocked;
		}

		if (!TL & !BL & TR & BR) {// 1100
			return Patch18.TopBottom;
		}

		if (TL & BL & !TR & !BR) {// 0011
			return Patch18.BottomTop;
		}

		if (!TL & BL & !TR & BR) {// 1010
			return Patch18.RightLeft;
		}

		if (TL & !BL & TR & !BR) {// 0101
			return Patch18.LeftRight;
		}

		if (!TL & BL & TR & BR) {// 1000
			return Patch18.TopLeft;
		}

		if (TL & !BL & TR & BR) {// 0100
			return Patch18.LeftBottom;
		}

		if (TL & BL & TR & !BR) {// 0001
			return Patch18.BottomRight;
		}

		if (!TL & !BL & !TR & !BR) {// 1111
			return Patch18.Free;
		}

		if (!TL & !BL & !TR & BR) {// 1110
			return Patch18.RightBottom;
		}

		if (!TL & !BL & TR & !BR) {// 0010
			return Patch18.TopRight;
		}

		if (!TL & BL & !TR & !BR) {// 0010
			return Patch18.BottomLeft;
		}

		if (TL & !BL & !TR & !BR) {// 0110
			return Patch18.LeftTop;
		}

		if (TL & BL & !TR & BR) {// 0110
			return Patch18.RightTop;
		}
		if (!TL & BL & TR & !BR) {
			return Patch18.BridgeRight;
		}

		if (TL & !BL & !TR & BR) {
			return Patch18.BridgeLeft;
		}

		return Patch18.ERR;
	}

// public void print (Rectangle area) {
// bool_field.print(area);
// }

}
