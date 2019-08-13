import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Alex1
 * Date: 24.04.2007
 * Time: 21:59:00
 * Формат 3DS-файла
 * Основная идея вот: файл 3DS состоит из блоков (chunks),
 * каждый из которых содержит какие-то полезные данные и, возможно, подблоки.
 * Большинство блоков содержит либо данные, либо подблоки, хотя есть и смешанные блоки.
 * Общий формат каждого блока такой:
 * смещение	длина	данные
 * ┌──┬──────┬────────────────────┐
 * │ 0	│	  2		│идентификатор типа блока, chunk_id	│
 * ├──┼──────┼────────────────────┤
 * │ 2	│	  4		│длина блока, chunk_len				│
 * │ 4	│chunk_len	│данные или подблоки				│
 * └──┴──────┴────────────────────┘
 *
 * Здесь я приведу лишь частичное описание, достаточное, впрочем, для того, чтобы прочитать из 3DS-файла 3D сцену и информацию о ее (сцены) анимации.

Итак, список идентификаторов типов нужных нам для этого блоков:
CHUNK_MAIN                = 0x4D4D; // [-] сцена
  CHUNK_OBJMESH           = 0x3D3D; // [-] всяческие объекты
    CHUNK_OBJBLOCK        = 0x4000; // [+] объект
      CHUNK_TRIMESH       = 0x4100; // [-] trimesh-объект
        CHUNK_VERTLIST    = 0x4110; // [+] список вершин
        CHUNK_FACELIST    = 0x4120; // [+] список граней
        CHUNK_FACEMAT     = 0x4130; // [+] материалы граней
        CHUNK_MAPLIST     = 0x4140; // [+] текстурные координаты
        CHUNK_TRMATRIX    = 0x4160; // [+] матрица перевода
      CHUNK_CAMERA        = 0x4700; // [+] объект-камера
  CHUNK_MATERIAL          = 0xAFFF; // [-] материал
    CHUNK_MATNAME         = 0xA000; // [+] название материала
    CHUNK_TEXTURE         = 0xA200; // [-] текстура материала
      CHUNK_MAPFILE       = 0xA300; // [+] имя файла текстуры
 CHUNK_KEYFRAMER          = 0xB000; // [-] информация об анимации
  CHUNK_TRACKINFO         = 0xB002; // [-] поведение объекта
    CHUNK_TRACKOBJNAME    = 0xB010; // [+] название этого объекта
    CHUNK_TRACKPIVOT      = 0xB013; // [+] центр вращения объекта
    CHUNK_TRACKPOS        = 0xB020; // [+] траектория объекта
    CHUNK_TRACKROTATE     = 0xB021; // [+] траектория вращения
                                    //     объекта
  CHUNK_TRACKCAMERA       = 0xB003; // [-] поведение камеры
    CHUNK_TRACKFOV        = 0xB023; // [+] поведение FOV камеры
    CHUNK_TRACKROLL       = 0xB024; // [+] поведение roll камеры
  CHUNK_TRACKCAMTGT       = 0xB004; // [-] поведение "цели" камеры

Конкретные данные содержат лишь блоки, отмеченные плюсом, остальные блоки состоят лишь из подблоков и их нам надо различать лишь потому, что нужные нам блоки данных - подблоки этих остальных. То есть, скажем, CHUNK_OBJMESH нам надо знать из-за того, что каждый объект в сцене описывается блоком CHUNK_OBJBLOCK, который появляется только как подблок CHUNK_OBJMESH, и просто игнорировать весь CHUNK_OBJMESH из-за этого нельзя. Кроме того, они задают структуру - например, все данные, что относятся к текущему блоку CHUNK_OBJBLOCK, относятся к текущему объекту, а подблоки этого блока уже могут быть разбросаны как угодно.

А теперь - относительно полное описание того, какие данные содержатся в каждом из блоков. Будут использоваться следующие обозначения:

char[]	ASCIIZ-строка (ASCII-строка, оканчивающаяся нулем)
byte 	8-битное беззнаковое целое число
word 	16-битное беззнаковое целое число
dword 	32-битное целое число
float 	32-битное число с плавающей запятой
vector	float x,z,y


Здесь (в самой последней строчке) опечатки нет! В файле 3DS везде изменен порядок следования y и z; то есть в файле данных ось y смотрит вглубь экрана, а ось z - вверх. Несмотря на то, что в самой 3D Studio ось y смотрит как раз вверх, а z - вдаль.

Блок: 	CHUNK_VERTLIST

Данные: 	список вершин текущего объекта

Формат: 	word num;             // число вершин
vector vertices[num]; // координаты каждой из вершин

Блок: 	CHUNK_FACELIST

Данные: 	список граней текущего объекта

Формат: 	word num;     // число граней
struct {      //
  word v0;    // номер первой вершины грани
  word v1;    // номер второй вершины грани
  word v2;    // номер третьей вершины грани
  word flags; // флаги грани
} faces[num]; // собственно список граней

Блок: 	CHUNK_FACEMAT

Данные: 	название материала и список тех граней объекта, которым назначен этот материал; таких блоков может быть несколько (грани с разными материалами в одном объекте), а может вообще не быть, если объекту материалы вообще не назначены (нетекстурированный объект)

Формат: 	char name[];         // название материала
word num;            // число граней из этого материала
word face_nums[num]; // список номеров граней из этого
                     // материала

Блок: 	CHUNK_MAPLIST

Данные: 	текстурные координаты вершин текущего объекта

Формат: 	word num;         // число вершин
struct {          //
  float u;        // координата текстуры U (по горизонтали)
  float v;        // координата текстуры V (по вертикали)
} texcoords[num]; // собственно список текстурных координат

Текстурные координаты задаются в диапазоне 0..1. Точка с U=0, V=0 - левый верхний край текстуры; U=1, V=1 - правый нижний; U=0.5, V=0.5 - центр текстуры.



Блок: 	CHUNK_TRMATRIX

Данные: 	содержит матрицу перевода объекта в некое абстрактное начальное состояние; в то, что было первоначально смоделировано. Требуется для корректного отображения анимации: дело в том, что все объекты в файле хранятся по состоянию на нулевой кадр, а анимация записана по отношению к "начальным" моделям. Т.е. при отображений нулевого кадра эта матрица не нужна, а при отображении всего набора кадров надо будет сначала перевести объекты в "начальное" состояние с помощью этой самой матрицы перевода и применять все преобразования (перемещения, повороты, etc) именно к "начальным" объектам.

Формат: 	float rotmatrix[3][3]; // матрица поворота
vector offset;         // смещение начального объекта

Для перевода объекта в "начальное" состояние надо сначала сдвинуть его назад, то есть на вектор -offset (НЕ на offset), а потом применить матрицу поворота rotmatr. Матрица записана построчно. Не забудьте - в ней y и z тоже везде обменены местами!




Блок: 	CHUNK_MATNAME
Данные: 	название материала
Формат: 	char[] name; // название материала

Блок: 	CHUNK_MAPFILE
Данные: 	имя файла с текстурой
Формат: 	char[] filename; // имя файла с текстурой

Блок: 	CHUNK_TRACKOBJNAME
Данные: 	название объекта, информация о поведении которого задается в текущем блоке CHUNK_TRACKINFO
Формат: 	char[] name;   // название объекта
word flags[2]; // флаги
word parent;   // номер объекта-родителя

Поле parent используется для построения иерархической структуры; дерева объектов. Каждому объекту присваивается его порядковый номер при следовании в файле, поле parent выставляется в -1, если у данного объекта нет родителя. Вот пример.
 объект | номер | parent
--------+-------+--------
    A   |   0   |   -1
    B   |   1   |    0                    A
    C   |   2   |    1          +---------+----+
    D   |   3   |    2          B         K    N
    E   |   4   |    1     +----+----+    +    +
    F   |   5   |    4     C    E    H    L    O
    G   |   6   |    5     +    +    +    +    +
    H   |   7   |    1     D    F    I    M    P
    I   |   8   |    7          +    +
    J   |   9   |    8          G    J
    K   |  10   |    0
    L   |  11   |   10
    M   |  12   |   11
    N   |  13   |    0
    O   |  14   |   13
    P   |  15   |   14

Насколько я понял, дерево используется следующим образом: если к какому-то узлу дерева применяется преобразование, то оно же автоматически применяется и ко всем узлам, "растущим" из этого. То есть, если объект B в нашем примере есть рука, а объекты C, D, E, F, G, H, I, J - пальцы, то при повороте руки пальцы должны повернуться автоматически, вместе с рукой. В результате блок CHUNK_TRACKROTATE для пальцев может быть пустым, а пальцы будут вращаться вместе с рукой.



Блок: 	CHUNK_TRACKPIVOT
Данные: 	координаты центра вращения объекта
Формат: 	vector pivotpoint; // координаты центра вращения

Центр вращения - это как раз та точка "начального" объекта, через которую надо будет провести ось вращения, задающуюся в блоке CHUNK_TRACKROTATE.



Блок: 	CHUNK_TRACKPOS
Данные: 	траектория объекта, заданная ключевыми значениями положения объекта
Формат: 	word flags;           // флаги
byte unknown[8];      // <неизвестно>
dword num;            // число ключевых значений
struct {              //
  dword frame;        // кадр данного ключевого значения
  word splineflags;   // флаги сплайна
  float[] splineinfo; // параметры сплайна (кол-во и тип
                      // зависит от значения splineflags)
  vector pos;         // положение объекта
} keys[num];          // собственно ключевые значения


Блок: 	CHUNK_TRACKROTATE
Данные: 	траектория вращения объекта, заданная ключевыми значениями вектора направления оси вращения и угла поворота относительно этой оси
Формат: 	word flags;           // флаги
byte unknown[8];      // <неизвестно>
dword num;            // число ключевых значений
struct {              //
  dword frame;        // кадр данного ключевого значения
  word splineflags;   // флаги сплайна
  float[] splineinfo; // параметры сплайна (кол-во и тип
                      // зависят от значения splineflags)
  float angle;        // угол поворота (в радианах)
  vector rotaxis;     // ось вращения
} keys[num];          // собственно ключевые значения

Только самое первое ключевое значение задает абсолютный поворот. Все последующие задают "добавочный" поворот, который надо добавить ко всем, уже сделанным. То есть, во второй ключевой позиции мы последовательно применяем повороты, заданные первым и вторым ключом, в третьей - первым, вторым и третьим, и так далее.


Блок: 	CHUNK_TRACKCAMERA
Данные: 	траектория камеры, заданная ключевыми значениями положения, угла зрения, ориентации камеры
Формат:
Состоит из подблоков CHUNK_TRACKOBJNAME, CHUNK_TRACKPOS, CHUNK_TRACKFOV, CHUNK_TRACKROLL и некоторых других, которые можно безболезненно игнорировать.

Блок: 	CHUNK_TRACKFOV
Данные: 	поведение FOV (угла зрения) камеры, заданное ключевыми значениями
Формат: 	word flags;           // флаги
byte unknown[8];      // <неизвестно>
dword num;            // число ключевых значений
struct {              //
  dword frame;        // кадр данного ключевого значения
  word splineflags;   // флаги сплайна
  float[] splineinfo; // параметры сплайна (кол-во и тип
                      // зависят от значения splineflags)
  float FOV;          // значение FOV
} keys[num];          // собственно ключевые значения

Блок: 	CHUNK_TRACKROLL
Данные: 	поведение roll (угла наклона) камеры, заданное ключевыми значениями
Формат: 	word flags;           // флаги
byte unknown[8];      // <неизвестно>
dword num;            // число ключевых значений
struct {              //
  dword frame;        // кадр данного ключевого значения
  word splineflags;   // флаги сплайна
  float[] splineinfo; // параметры сплайна (кол-во и тип
                      // зависят от значения splineflags)
  float roll;         // значение roll
} keys[num];          // собственно ключевые значения

Блок: 	CHUNK_TRACKCAMTGT
Данные: 	траектория "цели" камеры (точки, куда камера смотрит), заданная ключевыми значениями положения
Формат:
Состоит из подблоков CHUNK_TRACKOBJNAME, CHUNK_TRACKPOS и некоторых других, которые можно безболезненно игнорировать.
 */
public class File3DS
{
	static final int CHUNK_MAIN = 0x4D4D; // [-] сцена
	static final int CHUNK_OBJMESH = 0x3D3D; // [-] всяческие объекты
	static final int CHUNK_OBJBLOCK = 0x4000; // [+] объект
	static final int CHUNK_TRIMESH = 0x4100; // [-] trimesh-объект
	static final int CHUNK_VERTLIST = 0x4110; // [+] список вершин
	static final int CHUNK_FACELIST = 0x4120; // [+] список граней
	static final int CHUNK_FACEMAT = 0x4130; // [+] материалы граней
	static final int CHUNK_MAPLIST = 0x4140; // [+] текстурные координаты
	static final int CHUNK_TRMATRIX = 0x4160; // [+] матрица перевода
	static final int CHUNK_CAMERA = 0x4700; // [+] объект-камера
	static final int CHUNK_MATERIAL = 0xAFFF; // [-] материал
	static final int CHUNK_MATNAME = 0xA000; // [+] название материала
	static final int CHUNK_TEXTURE = 0xA200; // [-] текстура материала
	static final int CHUNK_MAPFILE = 0xA300; // [+] имя файла текстуры
	static final int CHUNK_KEYFRAMER = 0xB000; // [-] информация об анимации
	static final int CHUNK_TRACKINFO = 0xB002; // [-] поведение объекта
	static final int CHUNK_TRACKOBJNAME = 0xB010; // [+] название этого объекта
	static final int CHUNK_TRACKPIVOT = 0xB013; // [+] центр вращения объекта
	static final int CHUNK_TRACKPOS = 0xB020; // [+] траектория объекта
	static final int CHUNK_TRACKROTATE = 0xB021; // [+] траектория вращения
	//     объекта
	static final int CHUNK_TRACKCAMERA = 0xB003; // [-] поведение камеры
	static final int CHUNK_TRACKFOV = 0xB023; // [+] поведение FOV камеры
	static final int CHUNK_TRACKROLL = 0xB024; // [+] поведение roll камеры
	static final int CHUNK_TRACKCAMTGT = 0xB004; // [-] поведение "цели" камеры


	// -импорт объектов в сцену из 3DS файла
	public static boolean importScen3DS(InputStream in, Scen scen) throws IOException
	{
		int i, j;
		long l;
		long fileLenght;
		long objectsLenght;
		long objectLenght;
		long trimeshLenght;
		long materialLenght;
		long animLenght;
		long animCamLenght;
		long animCamTgtLenght;
		int num;
		boolean ok = true;
		try
		{
			i = readWord(in);
			fileLenght = readDWord(in) - 6;
			if (i == CHUNK_MAIN)
			{   // файл 3ds
				while (fileLenght > 0)
				{   // считываем содержимое CHUNK_MAIN
					i = readWord(in);
					l = readDWord(in);
					fileLenght -= l;	// вычесть длину текущего блока
					if (i == CHUNK_OBJMESH)
					{   // информация об объетах
						objectsLenght = l - 6;
						while (objectsLenght > 0)
						{	// считываем содержимое CHUNK_OBJMESH
							i = readWord(in);
							l = readDWord(in);
							objectsLenght -= l;	// вычесть длину подблока
							if (i == CHUNK_OBJBLOCK)
							{   // объект
								objectLenght = l;
								// имя объкта
								byte name[] = readChars(in);
								// чтение подблоков
								i = readWord(in);
								l = readDWord(in);
								if (i == CHUNK_TRIMESH)
								{   // обычный объект
									// добавляем объект
									if (scen.mTel == null)
									{   // объектов не было
										scen.mTel = new Telo[1];
										scen.mTel[0] = new Telo(new String(name, "ASCII"));
									} else
									{
										scen.mTel = Arrays.copyOf(scen.mTel, scen.mTel.length + 1);
										scen.mTel[scen.mTel.length - 1] = new Telo(new String(name, "ASCII"));
									}
									trimeshLenght = l;
									while (trimeshLenght > 0)
									{   // считываем содержимое CHUNK_TRIMESH
										i = readWord(in);
										l = readDWord(in);
										trimeshLenght -= l;	// вычесть длину подблока
										if (i == CHUNK_VERTLIST)
										{	// список вершин текущего объекта
											num = readWord(in);	// число вершин
											scen.mTel[scen.mTel.length - 1].mp = new Tochka[num];
											for (j = 0; j < num; j++)
											{	// координаты каждой из вершин
												scen.mTel[scen.mTel.length - 1].mp[j] = new Tochka(readFloat(in), readFloat(in), readFloat(in));
											}
										} else if (i == CHUNK_FACELIST)
										{	// список граней текущего объекта
											num = readWord(in);	// число граней
											scen.mTel[scen.mTel.length - 1].mg = new Gran[num];
											int a, b, c, flag;
											for (j = 0; j < num; j++)
											{	// список граней
												a = readWord(in);
												b = readWord(in);
												c = readWord(in);
												scen.mTel[scen.mTel.length - 1].mg[j] = new Gran(a, b, c);
												flag = readWord(in);	// флаги грани
												if ((flag & 1) == 1)
													scen.mTel[scen.mTel.length - 1].addRebro(a, c);
												if ((flag & 2) == 2)
													scen.mTel[scen.mTel.length - 1].addRebro(b, c);
												if ((flag & 4) == 4)
													scen.mTel[scen.mTel.length - 1].addRebro(a, b);
											}
										} else if (i == CHUNK_FACEMAT)
										{
											in.skip(l - 6);
											// название материала и список тех граней объекта, которым назначен этот материал
											//char name[];			// название материала
											//word num;				// число граней из этого материала
											//word face_nums[num];	// список номеров граней из этого материала
										} else if (i == CHUNK_MAPLIST)
										{
											in.skip(l - 6);
											// текстурные координаты вершин текущего объекта
											//word num;			// число вершин
											//struct {			//
											//  float u;			// координата текстуры U (по горизонтали)
											//  float v;			// координата текстуры V (по вертикали)
											//} texcoords[num];	// собственно список текстурных координат
										} else if (i == CHUNK_TRMATRIX)
										{
											in.skip(l - 6);
											// начальное состояние;
											//float rotmatrix[3][3];	// матрица поворота
											//vector offset;			// смещение начального объекта
										} else
										{	// неизвестный блок блока CHUNK_TRIMESH
											in.skip(l - 6);
										}
									}
								} else if (i == CHUNK_CAMERA)
								{
									in.skip(l - 6);
									// объектом является камера

								} else
								{	// ннеизвестный блок блока CHUNK_OBJBLOCK
									in.skip(l - 6);
								}
							} else
							{   // неизвестный блок блока CHUNK_OBJMESH
								in.skip(l - 6);
							}
						}
					} else if (i == CHUNK_MATERIAL)
					{
						in.skip(l - 6);
						// информация о материалах
						//CHUNK_MATNAME
						// название материала
						//char[] name; // название материала
						//CHUNK_MAPFILE
						//имя файла с текстурой
						//char[] filename; // имя файла с текстурой
					} else if (i == CHUNK_KEYFRAMER)
					{
						in.skip(l - 6);
						// информация об анмации

					} else if (i == CHUNK_TRACKCAMERA)
					{
						in.skip(l - 6);
						// анимация камеры

					} else if (i == CHUNK_TRACKCAMTGT)
					{
						in.skip(l - 6);
						// анимация камеры цели

					} else
					{   // неизвестный блок блока CHUNK_MAIN
						in.skip(l - 6);
					}
				}
			} else
			{   // файл не 3ds формата
				ok = false;
			}
		}
		catch (IOException e)
		{	// ошибка чтения файла
			ok = false;
		}
		return ok;
	}
	// чтение байта из файла
	private static int readByte(InputStream in) throws IOException
	{
		return in.read();
	}
	// чтение слова (два байт) из файла
	private static int readWord(InputStream in) throws IOException
	{
		return in.read() + (in.read() << 8);
	}
	// чтение двойново слова (четыре байта) из файла
	private static long readDWord(InputStream in) throws IOException
	{
		return (long) in.read() + (in.read() << 8) + (in.read() << 16) + (in.read() << 24);
	}
	// чтение строки (масив символов заканчивающийся нулем) из файла
	private static byte[] readChars(InputStream in) throws IOException
	{
		int i;
		int j = 0;
		byte name[] = new byte[10];
		while ((i = readByte(in)) != 0)
		{
			name[j] = (byte) i;
			j++;
			if (j >= name.length)
				name = Arrays.copyOf(name, name.length + 10);
		}
		return Arrays.copyOf(name, j);
	}
	// чтение переменной с плавающей точкой
	private static double readFloat(InputStream in) throws IOException
	{
		int ch1 = in.read();
		int ch2 = in.read();
		int ch3 = in.read();
		int ch4 = in.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return Float.intBitsToFloat(ch1 + (ch2 << 8) + (ch3 << 16) + (ch4 << 24));
	}

}
