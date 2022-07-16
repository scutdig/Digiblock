<?
    if ((elem.direction = "left") & (elem.barrelShifterMode = "logical")) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "LogicalLeft";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out = (in << shift);

endmodule
<?}?>
<?
    if ((elem.direction = "right") & (elem.barrelShifterMode = "logical")) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "LogicalRight";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out = (in >> shift);

endmodule
<?}?>
<?
    if ((elem.direction = "left") & (elem.barrelShifterMode = "arithmetic")) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "ArithmeticLeft";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out=($signed(in)<<<shift);

endmodule
<?}?>
<?
    if ((elem.direction = "right") & (elem.barrelShifterMode = "arithmetic")) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "ArithmeticRight";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out=($signed(in)>>>shift);

endmodule
<?}?>
<?
    if ((elem.direction = "left") & (elem.barrelShifterMode = "rotate") & (elem.Bits > 1)) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "RotateLeft";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

wire [(shiftBits-2):0] num;
assign num=shift[(shiftBits-2):0];
assign out = (in >> (Bits-num)| (in<<num));

endmodule
<?}?>
<?
    if ((elem.direction = "left") & (elem.barrelShifterMode = "rotate") & (elem.Bits = 1)) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "RotateLeft1";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out = (in >> (Bits-shift)| (in<<shift));

endmodule
<?}?>
<?
    if ((elem.direction = "right") & (elem.barrelShifterMode = "rotate") & (elem.Bits > 1)) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "RotateRight";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

wire [(shiftBits-2):0] num;
assign num=shift[(shiftBits-2):0];
assign out = (in << (Bits-num)| (in>>num));

endmodule
<?}?>
<?
    if ((elem.direction = "right") & (elem.barrelShifterMode = "rotate") & (elem.Bits = 1)) {
        generics[0] := "Bits";
        generics[1] := "shiftBits";
        moduleName = "RotateRight1";
-?>
module <?= moduleName ?> #(
parameter Bits = 4,
parameter shiftBits=3
)
(
    input [(Bits-1):0] in,
    input [(shiftBits-1):0] shift,
    output [(Bits - 1):0] out
);

assign out = (in << (Bits-shift)| (in>>shift));

endmodule
<?}?>
